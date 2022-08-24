package com.joc.todo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Slf4j
@RestController
public class SessionInfoController {

    @GetMapping("/sessions")
    public String getSessionInfo(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "No Session";
        }

        log.debug("SessionId = {} ", session.getId());
        log.debug("MaxInactiveInterval = {} ", session.getMaxInactiveInterval());
        log.debug("CreationTime = {} ", getFormattedDateTime(session.getCreationTime()));
        log.debug("LastAccessedTime = {} ", getFormattedDateTime(session.getLastAccessedTime()));
        log.debug("IsNew = {} ", session.isNew());
        return "Ok";
    }

    // 에펙
    private String getFormattedDateTime(long epochMilli) {
        return Instant.ofEpochMilli(epochMilli)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

}
