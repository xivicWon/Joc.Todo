package com.joc.todo.config;


import com.joc.todo.service.UserService;
import com.joc.todo.web.auth.TokenProvider;
import com.joc.todo.web.auth.UserAuthenticationHolder;
import com.joc.todo.web.interceptor.AuthenticationCheckInterceptor;
import com.joc.todo.web.interceptor.LogInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class InterceptorConfig implements WebMvcConfigurer {


    private final UserService userService;
    private final TokenProvider tokenProvider;
    private final UserAuthenticationHolder userAuthenticationHolder;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // /*  =>> /a (O), /ab (O), /a/b (X)
        // /**  =>> /a (O), /ab (O), /a/b (O)
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**");

//        registry.addInterceptor(new LoginCheckInterceptor(userService))
//                .order(2)
//                .addPathPatterns("/**")
//                .excludePathPatterns("/", "/sessions", "/auth/**");


        registry.addInterceptor(new AuthenticationCheckInterceptor(userService, tokenProvider, userAuthenticationHolder))
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/sessions", "/auth/**");


    }
}
