package com.joc.todo.web.filter;

import com.joc.todo.web.controller.session.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@Slf4j
public class LoginCheckFilter implements Filter {

    private static final String[] WHITE_LIST = {"/", "/service/sessions", "/service/auth/*"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String requestURI = httpServletRequest.getRequestURI();

        try {
            log.info("로그인 체크 필터 시작 = {}", requestURI);
            if (isLoginCheckTargetPath(requestURI)) {
                HttpSession session = httpServletRequest.getSession(false);
                if (session == null || session.getAttribute(SessionConst.SESSION_USER) == null) {
                    log.error("미사용자 사용 요청 = {}", requestURI);

                    HttpServletResponse httpServletResponse = (HttpServletResponse) response;
                    httpServletResponse.setStatus(401);
                    return;
                }
            }
            chain.doFilter(request, response);
        } finally {
            log.info("로그인 체크 필터 종료 = {}", requestURI);
        }
    }

    private boolean isLoginCheckTargetPath(String requestURI) {
        return !PatternMatchUtils.simpleMatch(WHITE_LIST, requestURI);
    }
}
