package com.joc.todo.controller;

import com.joc.todo.dto.TodoDto;
import com.joc.todo.entity.Todo;
import com.joc.todo.repository.TodoRepository;
import com.joc.todo.service.TodoService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@SpringBootTest
@Transactional
class TodoControllerTest {

    private static final String TEMP_USER_ID = "temp";
    @Autowired
    private TodoService todoService;

    @Autowired
    private TodoRepository todoRepository;

    Todo createTodo(String title) {
        return Todo.builder()
                .userId(TEMP_USER_ID)
                .title(title)
                .done(false)
                .build();
    }

    @Test
    @DisplayName("Todo 등록 후 목록 조회")
    void getTodoList() {
        //given
        todoService.create(createTodo("타이틀1"));
        todoService.create(createTodo("타이틀2"));
        todoService.create(createTodo("타이틀3"));

        //when
        List<Todo> list = todoService.getList(TEMP_USER_ID);
        List<TodoDto> todoDtos = TodoDto.todoDtoList(list);

        //then
        Assertions.assertThat(todoDtos).hasSize(3);
    }


    @Test
    @DisplayName("Todo 등록")
    void createTodo() {
        //given
        Todo todo = createTodo("타이틀");
        log.info("show Todos Id : {}", todo.getId());
        //when
        todoService.create(todo);
        //then`
        Assertions.assertThat(todo.getId()).isNotNull();
    }

    @Test
    @DisplayName("Todo 수정")
    void updateTodo() {
        //given
        Todo todo = createTodo("타이틀3");
        todoService.create(todo);
        todo.setDone(true);
        //when
        todoService.update(todo);
        //then`
        Assertions.assertThat(todo.isDone()).isTrue();
    }

    @Test
    @DisplayName("Todo 삭제")
    void deleteTodo() {
        //given
        Todo todo = createTodo("타이틀");
        todoService.create(todo);
        //when
        todoService.delete(todo);
        Optional<Todo> findTodo = todoRepository.findById(todo.getId());

        //then`
        Assertions.assertThat(findTodo).isNotPresent();

    }
}