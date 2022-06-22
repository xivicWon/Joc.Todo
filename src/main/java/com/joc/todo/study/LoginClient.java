package com.joc.todo.study;


import com.joc.todo.study.factory.LoginServiceFactory;
import com.joc.todo.study.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginClient {

    private final LoginServiceFactory loginServiceFactory;

    public void login(LoginType loginType) {
        LoginService loginService = loginServiceFactory.find(loginType);
        loginService.login();
    }
}

