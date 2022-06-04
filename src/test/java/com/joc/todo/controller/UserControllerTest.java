package com.joc.todo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joc.todo.dto.UserDto;
import com.joc.todo.entity.User;
import com.joc.todo.exception.ApplicationException;
import com.joc.todo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    UserService userService;

    @Autowired
    ObjectMapper objectMapper;
    // Override : 매서드 재정의 ( 상속관계에서 필요 )
    // Overloading : 동일 메서드 명에 파라미터수, 파라미터 타입에 따라 여러가지 매서드를 구현하는 방식.

    // 메서드 시그니쳐 : 메서드명 + 파라미터 타입 ==> Overloading 할때 필요한 점.
    // 아닌 것 : 반환타입, throws

    //
    @Test
    void signUp_access() throws Exception {
        // Given
        String urlTemplate = "/auth/signup";
        UserDto userDto = UserDto.builder()
                .username("재호")
                .email("xivic@kakao.com")
                .password("pass")
                .build();

        String content = objectMapper.writeValueAsString(userDto);

        doAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            ReflectionTestUtils.setField(args[0], "id", "아이디이이");
            return null;
        }).when(userService).signUp(any());

        // When && Then
        MvcResult mvcResult = mvc.perform(
                        post(urlTemplate).content(content)
                                .contentType(MediaType.APPLICATION_JSON)
//                                .contentType(MediaType.TEXT_PLAIN)
                )
                .andDo(print()) //상세내역 로그에 출력
                .andExpect(status().isOk())
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        log.debug(" >>> responseBody : {}", contentAsString);

        // handler : UserController
    }

    @Test
    void signUp_exception() throws Exception {
        // Given
        String urlTemplate = "/auth/signup";
        UserDto userDto = UserDto.builder()
                .username("재호")
                .password("pass")
                .build();

        String content = objectMapper.writeValueAsString(userDto);

        // Method Mocking
        doThrow(new ApplicationException("ex message")).when(userService).signUp(any());

        // When && Then
        mvc.perform(
                        post(urlTemplate).content(content)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isInternalServerError());

        // handler : UserController
    }

    @Test
    void login_success() throws Exception {
        // Given
        String urlTemplate = "/auth/login";
        UserDto userDto = UserDto.builder()
                .email("xivic@kakao.com")
                .password("pass")
                .build();

        String content = objectMapper.writeValueAsString(userDto);

        // Method Mocking
        doReturn(
                User.builder()
                        .id("test_id")
                        .email(userDto.getEmail())
                        .username(userDto.getUsername())
                        .build()
        ).when(userService)
                .logIn(anyString(), any());
        doThrow(new ApplicationException("ex message")).when(userService).signUp(any());

        // When && Then
        MvcResult mvcResult = mvc.perform(
                        post(urlTemplate).content(content)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        log.debug("{}", contentAsString);
        // handler : UserController
    }
}