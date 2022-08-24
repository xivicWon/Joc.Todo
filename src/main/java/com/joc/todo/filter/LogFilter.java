package com.joc.todo.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

// Servlet Container , Spring Container
// Http 요청 -> WAS -> 필터 -> <스프링 시작> 서블릿 -> 컨트롤러
// Http 요청 -> WAS -> 필터1 -> 필터2 -> 필터3 -> <스프링 시작> 서블릿 -> 컨트롤러


@Slf4j
public class LogFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
//        Filter.super.init(filterConfig);
        log.info("*** init()");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 상위 타입에서 하위 타입으로 Cast : 다운 캐스팅
        // 하위 타입에서 상위 타입으로 Cast : 업 캐스팅
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        StringBuffer requestURL = httpServletRequest.getRequestURL();
        String requestURI = httpServletRequest.getRequestURI();
        log.info("requestURL = {}", requestURL);
        log.info("requestURI = {}", requestURI);

        String uuid = UUID.randomUUID().toString();
        log.info(">>> Request [{}] [{}]", uuid, requestURI);
        try {
            chain.doFilter(request, response);
        } finally {
            log.info(">>> Response [{}] [{}]", uuid, requestURI);
        }
    }

    @Override
    public void destroy() {
//        Filter.super.destroy();
        log.info("*** destroy()");
    }
}
