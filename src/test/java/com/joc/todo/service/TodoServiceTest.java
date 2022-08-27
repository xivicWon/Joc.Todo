package com.joc.todo.service;

import com.joc.todo.data.entity.Todo;
import com.joc.todo.exception.ApplicationException;
import com.joc.todo.repository.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Slf4j
@Transactional  //SpringBootTest 의 기본설정은 Rollback 이다. 해서 넣어주는데 의미가 있다.
class TodoServiceTest {

    @Autowired
    TodoService todoService;
    @Autowired
    TodoRepository repository;
    private static final String USER_NAME = "temp";

    @Test
    void getList() {
        //given
        int createCount = 3;
        for (int i = 0; i < createCount; i++) {
            saveTodo("자바 공부하기", false);
        }
        //when
        List<Todo> userList = todoService.getList(USER_NAME);

        //then
        assertThat(userList).hasSize(createCount);
    }

    Todo saveTodo(String title, Boolean done) {
        Todo todo = Todo.builder()
                .userId(USER_NAME)
                .title(title)
                .done(done)
                .build();
        todoService.create(todo);
        return todo;
    }

    @Test
    @DisplayName("Todo 등록")
    void create() {
        //given
        Todo todo = Todo.builder()
                .userId(USER_NAME)
                .title("타이틀1번")
                .build();

        //when
        todoService.create(todo);

        //then
        Optional<Todo> result = repository.findById(todo.getId());
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isPositive();
        assertThat(result.get().getUserId()).isEqualTo(todo.getUserId());
        assertThat(result.get().getTitle()).isEqualTo(todo.getTitle());
    }

    @Test
    @DisplayName("숙제 : Todo가 null인 경우")
    void create_invalidEntity() {

        //given
        Todo todo = null;
        //when
        Executable executable = () -> {
            todoService.create(todo);
        };
        //then
        assertThrows(ApplicationException.class, executable);

    }

    @Test
    @DisplayName("숙제 : Todo의 userID가  null인 경우")
    void create_invalidUserID() {

        //given
        Todo todo = saveTodo("invalid UserID", false);
        todo.setUserId(null);
        //when
        Executable executable = () -> {
            todoService.create(todo);
        };
        //then
        assertThrows(ApplicationException.class, executable);

    }

    @Test
    @DisplayName("Todo 수정")
    void update() {
        //given
        int createCount = 3;
        for (int i = 0; i < createCount; i++) {
            saveTodo("자바 공부하기 >> No:" + (i + 1), false);
        }
        List<Todo> list = todoService.getList(USER_NAME);
        Todo updatingTodo = list.get(1);
        updatingTodo.setTitle("스프링 공부하기");
        updatingTodo.setDone(true);

        //when
        todoService.update(updatingTodo);

        //then
        Optional<Todo> updatedTodo = repository.findById(updatingTodo.getId());
        assertThat(updatedTodo).isPresent();
        assertThat(updatedTodo.get().getTitle()).isEqualTo(updatingTodo.getTitle());
        assertThat(updatedTodo.get().isDone()).isEqualTo(updatingTodo.isDone());

    }

    @Test
    @DisplayName("Todo 삭제")
    void delete() {
        //given
        Todo addTodo = saveTodo("삭제할 데이터", false);
        todoService.create(addTodo);
        Optional<Todo> byId = repository.findById(addTodo.getId());
        Todo savedTodo = byId.orElse(null);
        //when
        todoService.delete(savedTodo);

        //then
        int deleteID = savedTodo.getId();
        assertThat(repository.findById(deleteID)).isEmpty();
    }
}