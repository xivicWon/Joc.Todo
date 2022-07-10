package com.joc.todo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joc.todo.dto.TodoCreateDto;
import com.joc.todo.dto.TodoDeleteDto;
import com.joc.todo.dto.TodoUpdateDto;
import com.joc.todo.mapper.TodoMapper;
import com.joc.todo.service.TodoService;
import lombok.extern.slf4j.Slf4j;
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


    @Test
    void createTodo() throws Exception {
        // Given
        String urlTemplate = "/todo";
        TodoCreateDto dto = new TodoCreateDto("스프링 공부하기");
        String body = objectMapper.writeValueAsString(dto); // serialize : 객체 => Json String 으로 변환

        // When && Then
        mvc.perform(
                        post(urlTemplate).content(body)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print()) //상세내역 로그에 출력
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t"})
    void createTodoValidError(String title) throws Exception {
        // Given
        String urlTemplate = "/todo";
        TodoCreateDto dto = new TodoCreateDto(title);
        String body = objectMapper.writeValueAsString(dto); // serialize : 객체 => Json String 으로 변환

        // When && Then
        mvc.perform(
                        post(urlTemplate).content(body)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print()) //상세내역 로그에 출력
                .andExpect(status().isBadRequest());
    }


    @Test
    void updateTodo() throws Exception {
        // Given
        String urlTemplate = "/todo";
        TodoUpdateDto dto = new TodoUpdateDto(1, "스프링 공부하기", true);
        String body = objectMapper.writeValueAsString(dto); // serialize : 객체 => Json String 으로 변환

        // When && Then
        mvc.perform(
                        put(urlTemplate)
                                .content(body)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print()) //상세내역 로그에 출력
                .andExpect(status().isOk());
    }


    @ParameterizedTest
    @CsvSource(value = {":자바:false", "1::false", "::true"}, delimiter = ':')
    void updateTodoValidError(Integer Id, String title, boolean done) throws Exception {
        // Given
        String urlTemplate = "/todo";
        TodoUpdateDto dto = new TodoUpdateDto(Id, title, done);
        String body = objectMapper.writeValueAsString(dto); // serialize : 객체 => Json String 으로 변환

        // When && Then
        mvc.perform(
                        put(urlTemplate).content(body)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print()) //상세내역 로그에 출력
                .andExpect(status().isBadRequest());
    }


    @Test
    void deleteTodo() throws Exception {
        // Given
        String urlTemplate = "/todo";
        TodoDeleteDto dto = new TodoDeleteDto(1);
        String body = objectMapper.writeValueAsString(dto); // serialize : 객체 => Json String 으로 변환

        // When && Then
        mvc.perform(
                        delete(urlTemplate).content(body)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print()) //상세내역 로그에 출력
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @NullSource
    void deleteTodoValidError(Integer Id) throws Exception {
        // Given
        String urlTemplate = "/todo";
        TodoDeleteDto dto = new TodoDeleteDto(Id);
        String body = objectMapper.writeValueAsString(dto); // serialize : 객체 => Json String 으로 변환

        // When && Then
        mvc.perform(
                        delete(urlTemplate).content(body)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print()) //상세내역 로그에 출력
                .andExpect(status().isBadRequest());
    }


}