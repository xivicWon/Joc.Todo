package com.joc.todo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joc.todo.data.dto.TodoDto;
import com.joc.todo.data.mapper.TodoMapper;
import com.joc.todo.data.type.ResponseCode;
import com.joc.todo.service.TodoService;
import com.joc.todo.web.controller.TodoController;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@WebMvcTest({TodoController.class, TodoMapper.class})
class TodoControllerTest {


    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    TodoMapper todoMapper;
    @MockBean
    TodoService todoService;

    final String urlTemplate = "/todo/v7";

    @Test
    void createTodo() throws Exception {
        // Given
        TodoDto dto = TodoDto.builder().title("스프링 공부하기").build();
        String body = objectMapper.writeValueAsString(dto); // serialize : 객체 => Json String 으로 변환

        // When && Then
        mvc.perform(
                        post(urlTemplate).content(body)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print()) //상세내역 로그에 출력
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", Matchers.containsString(ResponseCode.SUCCESS.code())));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t"})
    void createTodoValidError(String title) throws Exception {
        // Given
        TodoDto dto = TodoDto.builder().title(title).build();
        String body = objectMapper.writeValueAsString(dto); // serialize : 객체 => Json String 으로 변환

        // When && Then
        mvc.perform(
                        post(urlTemplate).content(body)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print()) //상세내역 로그에 출력
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", Matchers.containsString(ResponseCode.BAD_REQUEST.code())));
    }


    @Test
    void updateTodo() throws Exception {
        // Given
        TodoDto dto = TodoDto.builder().id(1).title("스프링 공부하기").done(true).build();
        String body = objectMapper.writeValueAsString(dto); // serialize : 객체 => Json String 으로 변환

        // When && Then
        mvc.perform(
                        put(urlTemplate)
                                .content(body)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print()) //상세내역 로그에 출력
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", Matchers.containsString(ResponseCode.SUCCESS.code())));
        ;
    }


    @ParameterizedTest
    @CsvSource(value = {":자바:false", "1::false", "::true"}, delimiter = ':')
    void updateTodoValidError(Integer Id, String title, boolean done) throws Exception {
        // Given

        TodoDto dto = TodoDto.builder().id(Id).title(title).done(done).build();
        String body = objectMapper.writeValueAsString(dto); // serialize : 객체 => Json String 으로 변환

        // When && Then
        mvc.perform(
                        put(urlTemplate).content(body)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print()) //상세내역 로그에 출력
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", Matchers.containsString(ResponseCode.BAD_REQUEST.code())));
    }


    @Test
    void deleteTodo() throws Exception {
        // Given
        TodoDto dto = TodoDto.builder().id(1).build();
        String body = objectMapper.writeValueAsString(dto); // serialize : 객체 => Json String 으로 변환

        // When && Then
        mvc.perform(
                        delete(urlTemplate).content(body)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print()) //상세내역 로그에 출력
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", Matchers.containsString(ResponseCode.SUCCESS.code())));
    }

    @ParameterizedTest
    @NullSource
    void deleteTodoValidError(Integer Id) throws Exception {
        // Given
        TodoDto dto = TodoDto.builder().id(Id).build();
        String body = objectMapper.writeValueAsString(dto); // serialize : 객체 => Json String 으로 변환

        // When && Then
        mvc.perform(
                        delete(urlTemplate).content(body)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print()) //상세내역 로그에 출력
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", Matchers.containsString(ResponseCode.BAD_REQUEST.code())));
    }


}