package com.joc.todo.web.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

// 웹(Tomcat/SpringMVC) 공통 관심사
// 서블릿 필터 : 서블릿이 제공하는 기술
// 스프링 인터셉터 : 스프링 MVC 가 제공하는 기술.


// Servlet Container , Spring Container
// Http 요청 -> WAS -> 필터 -> <스프링 시작> 서블릿 -> 컨트롤러
// Http 요청 -> WAS -> 필터1 -> 필터2 -> 필터3 -> <스프링 시작> 서블릿 ->
//       인터셉터1 -> 인터셉터2 -> 컨트롤러


@Slf4j
public class LogInterceptor implements HandlerInterceptor {

    private static final String LOG_ID = "logId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String method = request.getMethod();
        String requestURI = request.getRequestURI();
        String logId = UUID.randomUUID().toString();
        request.setAttribute(LOG_ID, logId);

        log.info(">>> RequestURI = {}", requestURI);
        log.info(">>> Request [{}] [{}] [{}]", logId, method, requestURI);
        log.info(">>> Controller {}", handler);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        String requestURI = request.getRequestURI();
        String logId = (String) request.getAttribute(LOG_ID);
        log.info(">>> PostHandle [{}] [{}]", logId, requestURI);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        String method = request.getMethod();
        String requestURI = request.getRequestURI();
        String logId = (String) request.getAttribute(LOG_ID);
        log.info(">>> AfterCompletion [{}] [{}] [{}]", logId, method, requestURI);

    }


}
