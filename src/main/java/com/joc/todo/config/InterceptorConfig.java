package com.joc.todo.config;


import com.joc.todo.service.UserService;
import com.joc.todo.web.interceptor.LogInterceptor;
import com.joc.todo.web.interceptor.LoginCheckInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class InterceptorConfig implements WebMvcConfigurer {


    private final UserService userService;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // /*  =>> /a (O), /ab (O), /a/b (X)
        // /**  =>> /a (O), /ab (O), /a/b (O)
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**");

        registry.addInterceptor(new LoginCheckInterceptor(userService))
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/sessions", "/auth/**");
    }
}
