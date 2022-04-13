package com.joc.todo.repository;

import com.joc.todo.entity.Todo;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
@Transactional
class TodoJpaEmRepositoryTest {

//    @Autowired
//    EntityManager em;
    @Autowired
    TodoRepository repository;

    private static final String USER_NAME = "temp";

    Todo saveTodo(String title, Boolean done){
        Todo todo = Todo.builder()
                .userId(USER_NAME)
                .title(title)
                .done(done)
                .build();
        repository.save(todo);
        return todo;
    }

    @BeforeEach
    void beforeEach(){
        log.warn("!!! beforeeach truncated !!!");
        repository.truncate();
    }


    @Test
    void findAll() {
        // Given
        saveTodo("자바공부하기" , false);
        saveTodo("자바공부하기2" , false);
        saveTodo("자바공부하기3" , false);

        // When
        List<Todo> todos = repository.findAll();

        // Then
        assertThat(todos).hasSize(3);
    }

    @Test
    @DisplayName("사용자 아이디로 찾기")
    void findByUserId() {
        // Given
        Todo todo = saveTodo("자바공부하기" , false);
        Todo todo2 = saveTodo("자바공부하기" , false);

        repository.save(todo);
        repository.save(todo2);

        // When
        List<Todo> todos =  repository.findByUserId(USER_NAME);

        // Then
        for( Todo t : todos){
            log.error(" todos {}" , t);
        }

        assertThat(todos).hasSize(2);
        assertThat(todos.get(0).getId()).isEqualTo(todo.getId());
    }

    @Test
    void findById() {
        // Given
        Todo todo = saveTodo("자바공부하기" , false);

        // When
        Optional<Todo> optionalTodo =  repository.findById(todo.getId());
        Todo resultTodo = optionalTodo.orElse(null);

        // Then
        assertThat(optionalTodo).isPresent();
        assertThat(resultTodo).isSameAs(todo);
    }


    @Test
    void findById_NotFound() {
        // Given
        int id = -1;

        // When
        Optional<Todo> optionalTodo =  repository.findById(id);

        // Then
        assertThat(optionalTodo).isEmpty();
    }



    @Test
    @Rollback(value = false)
    void save() {
        // Given
        Todo todo = saveTodo("자바공부하기" , false);

        // When
//        Optional<Todo> optionalTodo =  repository.findById(todo.getId());

        // Then
//        Todo resultTodo = optionalTodo.orElse(null);
//        assertThat(resultTodo).isEqualTo(todo);
        assertThat(repository.findById(todo.getId())).isPresent();
    }

    @Test
    @DisplayName("데이터 삭제")
    void delete() {
        // Given
        Todo todo = saveTodo("자바공부하기-삭제용" , true);

        // When
        repository.delete(todo);
        Optional<Todo> optTodo = repository.findById(todo.getId());

        // Then
        assertThat(optTodo).isEmpty();
    }

    @Test
    @DisplayName("데이터 삭제 후 해당 아이디로 다시 찾을때 Empty가 나와야함.")
    void deleteById() {
        // Given
        Todo todo = saveTodo("자바공부하기-삭제용" , false);

        // When
        repository.deleteById(todo.getId());
        Optional<Todo> optTodo = repository.findById(todo.getId());

        // Then
        assertThat(optTodo).isEmpty();
    }

    @Test
    @DisplayName("등록되지 않는 아이디로 검색시 Empty가 나와야 함.")
    void deleteById_NotRegist() {
        // Given
        Integer id = -1;

        // When
        repository.deleteById(id);
        Optional<Todo> optTodo = repository.findById(id);

        // Then
        assertThat(optTodo).isEmpty();
    }
}