package com.joc.todo.study.service;

import com.joc.todo.study.LoginType;

public interface LoginService {

    boolean supports(LoginType loginType);

    void login();

}
