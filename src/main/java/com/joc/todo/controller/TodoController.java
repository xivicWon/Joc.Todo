package com.joc.todo.controller;

import com.joc.todo.entity.Todo;
import com.joc.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// 스프링 3 계층  @Service @Controller @Repository

//@Controller
//@ResponseBody
@RestController
@RequiredArgsConstructor
public class TodoController {

    private static final String TEMP_USER_ID = "temp";
    private final TodoService todoService;

    // http method : GET POST PUT PATCH DELETE OPTIONS HEAD TRACE CONNECT
    @GetMapping("/todo")
    public ResponseEntity<List<Todo>> getTodoList(){
        List<Todo> todoList = todoService.getList(TEMP_USER_ID);
        return ResponseEntity.ok().body(todoList);
    }

    @PostMapping("/todo")
    public ResponseEntity<List<Todo>> createTodo( @RequestParam(name="title") String title ){
        Todo build = Todo.builder()
                .userId(TEMP_USER_ID)
                .title(title)
                .build();
        todoService.create(build);
        return getTodoList();
    }

    @PutMapping("/todo")
    public ResponseEntity<List<Todo>> modifyTodo(Integer id, String title, Boolean done){
        Todo build = Todo.builder()
                .userId(TEMP_USER_ID)
                .id(id)
                .title(title)
                .done(done)
                .build();
        todoService.update(build);
        return getTodoList();
    }

    @DeleteMapping("/todo")
    public ResponseEntity<List<Todo>> removeTodo(Integer id){
        Todo build = Todo.builder()
                .userId(TEMP_USER_ID)
                .id(id)
                .build();
        todoService.delete(build);
        return getTodoList();
    }
}
