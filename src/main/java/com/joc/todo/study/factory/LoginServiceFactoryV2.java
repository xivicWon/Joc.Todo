package com.joc.todo.study.factory;

import com.joc.todo.study.LoginType;
import com.joc.todo.study.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.NoSuchElementException;


@Slf4j
//@Component
@RequiredArgsConstructor
public class LoginServiceFactoryV2 implements LoginServiceFactory {

    private final List<LoginService> loginServices;

    @Override
    public LoginService find(LoginType loginType) {
        // Version 1
//        for (LoginService loginService : loginServices) {
//            if (loginService.supports(loginType)) {
//                return loginService;
//            }
//        }
        // Version 2
        return loginServices.stream()
                .filter(loginService -> loginService.supports(loginType))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("노 써치 엘레멘트 익셉션 ~  : " + loginType));
    }
}


