package com.joc.todo.controller;

import com.joc.todo.controller.validation.marker.TodoValidationGroup;
import com.joc.todo.dto.TodoDto;
import com.joc.todo.dto.response.ResponseDto;
import com.joc.todo.dto.response.ResponseResultDto;
import com.joc.todo.entity.Todo;
import com.joc.todo.mapper.TodoMapper;
import com.joc.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// 스프링 3 계층  @Service @Controller @Repository

@Slf4j
//@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/todo")
//    @CrossOrigin(origins = "http://localhost:3000/", maxAge = 3600, methods = {RequestMethod.PUT, RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE})
public class TodoController {

    private static final String TEMP_USER_ID = "temp";
    private final TodoService todoService;
    private final TodoMapper todoMapper;
    // http method : GET POST PUT PATCH DELETE OPTIONS HEAD TRACE CONNECT

    @GetMapping
    public ResponseDto<List<TodoDto>> getTodoList() {
        ResponseResultDto<List<TodoDto>> responseResultDto = ResponseResultDto.of(
                todoMapper.toDtoList(todoService.getList(TEMP_USER_ID))
        );
        return ResponseDto.of(responseResultDto);

    }

    // R&R -> Role & Responsibility ( 역할과 책임 )
    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto<List<TodoDto>> createTodo(
            @RequestBody @Validated({TodoValidationGroup.Creation.class}) TodoDto todoDto) {

        log.info("MY_INFO > todoDto : {}", todoDto);

        Todo todo = todoMapper.toEntity(todoDto);
        todo.setUserId(TEMP_USER_ID);
        todoService.create(todo);
        return getTodoList();
    }


    // R&R -> Role & Responsibility ( 역할과 책임 )
    @PostMapping(
            consumes = MediaType.TEXT_PLAIN_VALUE,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public String createTodo22(
            @RequestBody String str) {

        log.info("consumes, produces 테스트 >>>  {}", str);
        return "createTodo22";
    }


    @PutMapping
    public ResponseDto<List<TodoDto>> updateTodo(
            @RequestBody @Validated({TodoValidationGroup.Update.class}) TodoDto todoDto) {
        Todo newTodo = todoMapper.toEntity(todoDto);
        newTodo.setUserId(TEMP_USER_ID);
        todoService.update(newTodo);
        return getTodoList();
    }

    @DeleteMapping
    public ResponseDto<List<TodoDto>> deleteTodo(
            @RequestBody @Validated({TodoValidationGroup.Deletion.class}) TodoDto todoDto) {
        Todo todo = todoMapper.toEntity(todoDto);
        todo.setUserId(TEMP_USER_ID);
        todoService.delete(todo);
        return getTodoList();
    }
}
