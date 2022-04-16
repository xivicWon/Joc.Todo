package com.joc.todo.controller;

import com.joc.todo.dto.TodoDto;
import com.joc.todo.entity.Todo;
import com.joc.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// 스프링 3 계층  @Service @Controller @Repository

@Slf4j
@RestController
@RequiredArgsConstructor

public class TodoController {

    private static final String TEMP_USER_ID = "temp";
    private final TodoService todoService;

    // http method : GET POST PUT PATCH DELETE OPTIONS HEAD TRACE CONNECT
    @GetMapping("/todo")
    public ResponseEntity<List<TodoDto>> getTodoList() {
        List<Todo> todoList = todoService.getList(TEMP_USER_ID);

        // 객체를 Json String 으로 변환 > HttpMessageCoverter (Serialize / Serializer)
        // Json String을 객체로 변환 > HttpMessageCoverter (Deserialize / Deserializer)
        return ResponseEntity.ok().body(TodoDto.todoDtoList(todoList));
    }

    @PostMapping("/todo")
    public ResponseEntity<List<TodoDto>> createTodo(
            @RequestBody Todo todo) {
        log.info("MY_INFO > todo : {}", todo);
        todo.setUserId(TEMP_USER_ID);
        todoService.create(todo);
        return getTodoList();
    }

    @PutMapping("/todo")
    public ResponseEntity<List<TodoDto>> modifyTodo(Integer id, String title, Boolean done) {
        Todo build = Todo.builder().userId(TEMP_USER_ID).id(id).title(title).done(done).build();
        todoService.update(build);
        return getTodoList();
    }

    @DeleteMapping("/todo")
    public ResponseEntity<List<TodoDto>> removeTodo(Integer id) {
        Todo build = Todo.builder().userId(TEMP_USER_ID).id(id).build();
        todoService.delete(build);
        return getTodoList();
    }
}
