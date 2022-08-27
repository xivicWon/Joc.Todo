package com.joc.todo.config;

import com.joc.todo.web.argument.LoginUserArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration // @Component + 추가 기능 (BCI)
public class WebMvcConfig implements WebMvcConfigurer {
    private final int MAX_AGE_SECOND = 3600;    // 매직넘버 or 매직상수

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // /** , /* 차이!
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(MAX_AGE_SECOND);
    }


    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginUserArgumentResolver());
    }
}
