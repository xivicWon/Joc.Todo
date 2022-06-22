package com.joc.todo.study.service;

import com.joc.todo.study.LoginType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
class GoogleLoginService implements LoginService {
    @Override
    public boolean supports(LoginType loginType) {
        return loginType == LoginType.GOOGLE;
    }

    @Override
    public void login() {
        log.debug("called login()");
    }
}
