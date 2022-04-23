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
@RequestMapping("/todo")
public class TodoController {

    private static final String TEMP_USER_ID = "temp";
    private final TodoService todoService;

    // http method : GET POST PUT PATCH DELETE OPTIONS HEAD TRACE CONNECT
    @GetMapping
    public ResponseEntity<List<TodoDto>> getTodoList() {
//        List<Todo> todoList = todoService.getList(TEMP_USER_ID);

        // 객체를 Json String 으로 변환 > HttpMessageCoverter (Serialize / Serializer)
        // Json String을 객체로 변환 > HttpMessageCoverter (Deserialize / Deserializer)
//        type 1
//        List<TodoDto> todoDtos = new ArrayList<>();
//        for (Todo todo : todoList) {
//            TodoDto todoDto = TodoDto.builder()
//                    .id(todo.getId())
//                    .title(todo.getTitle())
//                    .done(todo.isDone())
//                    .build();
//            todoDtos.add(todoDto);
//        }

//        type 2
//        List<TodoDto> todoDtos = new ArrayList<>();
//        for (Todo todo : todoList) {
//            TodoDto todoDto = TodoDto.toDto(todo);
//            todoDtos.add(todoDto);
//        }

//        type 3
//        List<TodoDto> todoDtos = TodoDto.todoDtoList(todoList);
//        return ResponseEntity.ok().body(TodoDto.todoDtoList(todoList));

        return ResponseEntity.ok().body(TodoDto.todoDtoList(todoService.getList(TEMP_USER_ID)));

    }

    // R&R -> Role & Responsibility ( 역할과 책임 )
    @PostMapping
    public ResponseEntity<List<TodoDto>> createTodo(
            @RequestBody TodoDto todoDto) {

        log.info("MY_INFO > todoDto : {}", todoDto);
        Todo todo = Todo.from(todoDto);
        todoService.create(todo);
        return getTodoList();
    }

    @PutMapping
    public ResponseEntity<List<TodoDto>> updateTodo(
            @RequestBody TodoDto todoDto) {
        todoService.update(Todo.from(todoDto));
        return getTodoList();
    }

    @DeleteMapping
    public ResponseEntity<List<TodoDto>> deleteTodo(
            @RequestBody TodoDto todoDto) {
        Todo todo = Todo.from(todoDto);
//        Todo build = Todo.builder().id(todoDto.getId()).userId(TEMP_USER_ID).build();
        todoService.delete(todo);
        return getTodoList();
    }
}
