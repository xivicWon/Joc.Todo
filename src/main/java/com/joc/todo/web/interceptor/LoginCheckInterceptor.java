package com.joc.todo.web.interceptor;


import com.joc.todo.data.entity.User;
import com.joc.todo.exception.RequiredAuthenticationException;
import com.joc.todo.service.UserService;
import com.joc.todo.web.controller.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@RequiredArgsConstructor
public class LoginCheckInterceptor implements HandlerInterceptor {

    private final UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            return true;
        }
        String requestURI = request.getRequestURI();
        log.info("인증 체크 인터셉터 시작 = {}", requestURI);

        HttpSession session = request.getSession(false);
        checkLoginSession(session);
        return true;
    }

    private void checkLoginSession(HttpSession session) {
        checkSessionAttribute(session);

        User user = (User) session.getAttribute(SessionConst.SESSION_USER);
        String userId = user.getId();

        checkLoginUser(userId);
    }

    private void checkSessionAttribute(HttpSession session) {
        if (session == null || session.getAttribute(SessionConst.SESSION_USER) == null) {
            throwNoLoginUser();
        }
    }

    private void checkLoginUser(String userID) {

        if (!StringUtils.hasText(userID)) {
            throwNoLoginUser();
        }
        if (!userService.existsUser(userID)) {
            throwNoLoginUser();
        }
    }

    private void throwNoLoginUser() {
        throw new RequiredAuthenticationException("로그인을 하셔야 합니다.");
    }

}
