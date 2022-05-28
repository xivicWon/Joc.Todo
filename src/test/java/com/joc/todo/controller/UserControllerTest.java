package com.joc.todo.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@Slf4j
@WebMvcTest(UserController.class)
class UserControllerTest {

    MockMvc mvc;

    @Test
    void signUp_access() {

    }
}