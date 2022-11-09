package com.joc.todo.web.auth;

import com.joc.todo.data.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserAuthenticationHolder implements AuthenticationHolder {

    private User user;

    @Override
    public User get() {
        return user;
    }

    @Override
    public void set(Object data) {
        this.user = (User) data;
    }
}
