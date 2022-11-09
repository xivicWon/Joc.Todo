package com.joc.todo.web.interceptor;


import com.joc.todo.data.entity.User;
import com.joc.todo.exception.InvalidUserProblemException;
import com.joc.todo.exception.RequiredAuthenticationException;
import com.joc.todo.service.UserService;
import com.joc.todo.web.auth.TokenProvider;
import com.joc.todo.web.auth.UserAuthenticationHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RequiredArgsConstructor
public class AuthenticationCheckInterceptor implements HandlerInterceptor {

    private final UserService userService;
    private final TokenProvider tokenProvider;
    private final UserAuthenticationHolder userAuthenticationHolder;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            return true;
        }

        String requestURI = request.getRequestURI();
        log.info("인증 체크 인터셉터 시작 = {}", requestURI);

        String authToken = parseAuthToken(request);

        String userId = validateAuthTokenAndGetUserId(authToken);

        User user = validateUserIdAndGetUser(userId);

        setUserToAuthenticationHolder(user);

        return true;
    }

    private String parseAuthToken(HttpServletRequest request) {

        // 토큰 가져오기
        String authToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info("Auth Token : {} ", authToken);

        // 유효한 토큰 확인
        if (!StringUtils.hasText(authToken) || !authToken.startsWith("Bearer ")) {
            throwNoLoginUser();
        }
        return authToken.substring(7);
    }

    private String validateAuthTokenAndGetUserId(String authToken) {
        if (StringUtils.hasText(authToken)) {
            throwNoLoginUser();
        }
        return tokenProvider.validateAndGetUserId(authToken);
    }

    private User validateUserIdAndGetUser(String userId) {
        return userService.getUser(userId).orElseThrow(() ->
                new InvalidUserProblemException("유효한 회원이 아닙니다.")
        );
    }

    private void throwNoLoginUser() {
        throw new RequiredAuthenticationException("로그인을 하셔야 합니다.");
    }

    private void setUserToAuthenticationHolder(User user) {
        userAuthenticationHolder.set(user);
    }

}
