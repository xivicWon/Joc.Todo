package com.joc.todo.web.controller;

import com.joc.todo.data.dto.TodoDto;
import com.joc.todo.data.dto.response.ResponseDto;
import com.joc.todo.data.dto.response.ResponseResultDto;
import com.joc.todo.data.entity.Todo;
import com.joc.todo.data.entity.User;
import com.joc.todo.data.mapper.TodoMapper;
import com.joc.todo.service.TodoService;
import com.joc.todo.web.argument.LoginUser;
import com.joc.todo.web.controller.validation.marker.TodoValidationGroup;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/todo/v7")
public class TodoV7Controller {

    private final TodoService todoService;
    private final TodoMapper todoMapper;

    @GetMapping
    public ResponseDto<List<TodoDto>> getTodoList(
            @LoginUser User loginUser
    ) {
        return getRealTodoList(loginUser.getId());
    }

    private ResponseDto<List<TodoDto>> getRealTodoList(String userId) {
        ResponseResultDto<List<TodoDto>> responseResultDto = ResponseResultDto.of(
                todoMapper.toDtoList(todoService.getList(userId))
        );
        return ResponseDto.of(responseResultDto);
    }

    // R&R -> Role & Responsibility ( 역할과 책임 )
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto<List<TodoDto>> createTodo(
            @RequestBody @Validated({TodoValidationGroup.Creation.class}) TodoDto todoDto,
            @LoginUser User loginUser) {
        log.info("MY_INFO > todoDto : {}", todoDto);
        Todo todo = todoMapper.toEntity(todoDto);
        todo.setUserId(loginUser.getId());
        todoService.create(todo);
        return getRealTodoList(loginUser.getId());
    }

    @PutMapping
    public ResponseDto<List<TodoDto>> updateTodo(
            @RequestBody @Validated({TodoValidationGroup.Update.class}) TodoDto todoDto,
            @LoginUser User loginUser) {
        Todo newTodo = todoMapper.toEntity(todoDto);
        newTodo.setUserId(loginUser.getId());
        todoService.update(newTodo);
        return getRealTodoList(loginUser.getId());
    }

    @DeleteMapping
    public ResponseDto<List<TodoDto>> deleteTodo(
            @RequestBody @Validated({TodoValidationGroup.Deletion.class}) TodoDto todoDto,
            @LoginUser User loginUser) {
        Todo todo = todoMapper.toEntity(todoDto);
        todo.setUserId(loginUser.getId());
        todoService.delete(todo);
        return getRealTodoList(loginUser.getId());
    }
}
