package com.joc.todo.repository;

import com.joc.todo.entity.Todo;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@Transactional
class TodoJpaEmRepositoryTest {

    @Autowired
    EntityManager em;
    @Autowired
    TodoRepository repository;

    private static final String USER_NAME = "temp";

    @Test
    void findAll() {
        // Given

        Todo todo = Todo.builder()
                .userId(USER_NAME)
                .title("자바 공부하기1")
                .done(false)
                .build();
        Todo todo2 = Todo.builder()
                .userId(USER_NAME)
                .title("자바 공부하기2")
                .done(false)
                .build();
        Todo todo3 = Todo.builder()
                .userId(USER_NAME)
                .title("자바 공부하기3")
                .done(false)
                .build();
        repository.save(todo);
        repository.save(todo2);
        repository.save(todo3);

        // When
        List<Todo> todos =  repository.findAll();

        // Then
        assertThat(todos).hasSize(3);
    }

    @Test
    void findByUserId() {
        // Given
        Todo todo = Todo.builder()
                .userId(USER_NAME)
                .title("자바 공부하기1")
                .done(false)
                .build();
        repository.save(todo);

        Todo todo2 = Todo.builder()
                .userId(USER_NAME)
                .title("자바 공부하기1")
                .done(false)
                .build();

        repository.save(todo2);
        log.info("---->>> todo [0] {} ", todo.getId());
        log.info("---->>> todo [1] {} ", todo2.getId());

        // When
        List<Todo> todos =  repository.findByUserId(USER_NAME);

        // Then
        assertThat(todos).hasSize(2);
        assertThat(todos.get(0).getId()).isEqualTo(todo.getId());
    }

    @Test
    void findById() {
        // Given
        Todo todo = Todo.builder()
                .userId(USER_NAME)
                .title("자바 공부하기1")
                .done(false)
                .build();
        repository.save(todo);
        // When
        Optional<Todo> optionalTodo =  repository.findById(todo.getId());
        Todo resultTodo = optionalTodo.orElse(null);
        // Then
        assertThat(resultTodo).isEqualTo(todo);
    }


    @Test
    @Rollback(value = false)
    void save() {
        // Given
        Todo todo = Todo.builder()
                .userId(USER_NAME)
                .title("자바 공부하기2")
                .done(false)
                .build();

//        Todo todo2 = Todo.builder()
//                .userId(USER_NAME)
//                .title("자바 공부하기3")
//                .done(false)
//                .build();

        // When
        repository.save(todo);
//        repository.save(todo2);

        // Then
        Optional<Todo> optionalTodo =  repository.findById(todo.getId());
        Todo resultTodo = optionalTodo.orElse(null);
        assertThat(resultTodo).isEqualTo(todo);

    }


    @Disabled
    @Test
    void delete() {
        // Given
        // When
        // Then
    }

    @Disabled
    @Test
    void deleteById() {
        // Given
        // When
        // Then
    }
}