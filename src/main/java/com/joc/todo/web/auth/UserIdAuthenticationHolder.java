package com.joc.todo.web.auth;

import org.springframework.stereotype.Component;

@Component
public class UserIdAuthenticationHolder implements AuthenticationHolder {
    private String userId;

    @Override
    public String get() {
        return userId;
    }

    @Override
    public void set(Object userId) {
        this.userId = (String) userId;

    }
}
