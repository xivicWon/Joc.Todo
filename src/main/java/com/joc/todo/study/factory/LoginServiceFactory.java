package com.joc.todo.study.factory;

import com.joc.todo.study.LoginType;
import com.joc.todo.study.service.LoginService;

public interface LoginServiceFactory {

    LoginService find(LoginType loginType);
}
