package com.joc.todo.web.controller.session;


import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionManager {

    private final Map<String, Object> sessionStore = new ConcurrentHashMap<>();

    public void createSession(Object value, HttpServletResponse response) {
        String sessionId = UUID.randomUUID().toString();
        sessionStore.put(sessionId, value);

        Cookie cookie = new Cookie(SessionConst.SESSION_USER, sessionId);
        cookie.setPath("/");

        response.addCookie(cookie);
    }

    public Object getSession(HttpServletRequest request) {
        return getSessionCookieId(request)
                .map(sessionStore::get)
                .orElse(null);
    }

    @NotNull
    private Optional<String> getSessionCookieId(HttpServletRequest request) {
        return Arrays.stream(Optional.ofNullable(request.getCookies()).orElse(new Cookie[]{}))
                .filter(cookie -> cookie.getName().equals(SessionConst.SESSION_USER))
                .findFirst()
                .map(Cookie::getValue);
    }

    public void expireSession(HttpServletRequest request) {
        getSessionCookieId(request).ifPresent(sessionStore::remove);
    }
}

