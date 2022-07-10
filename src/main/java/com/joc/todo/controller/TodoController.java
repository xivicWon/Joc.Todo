package com.joc.todo.controller;

import com.joc.todo.dto.TodoCreateDto;
import com.joc.todo.dto.TodoDeleteDto;
import com.joc.todo.dto.TodoDto;
import com.joc.todo.dto.TodoUpdateDto;
import com.joc.todo.dto.response.ResponseDto;
import com.joc.todo.dto.response.ResponseResultDto;
import com.joc.todo.entity.Todo;
import com.joc.todo.mapper.TodoMapper;
import com.joc.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

//        ResponseResultDto<List<TodoDto>> responseResultDto = ResponseResultDto.of(
//                TodoDto.todoDtoList(
//                        todoService.getList(TEMP_USER_ID)
//                )
//        );
        ResponseResultDto<List<TodoDto>> responseResultDto = ResponseResultDto.of(
                todoMapper.toDtoList(todoService.getList(TEMP_USER_ID))
        );
        return ResponseDto.of(responseResultDto);

//        return ResponseDto.of(ResponseResultDto.of(TodoDto.todoDtoList(todoService.getList(TEMP_USER_ID)), 1));
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
//        return ResponseEntity.ok().body(TodoDto.todoDtoList(todoService.getList(TEMP_USER_ID)));

    }

    // R&R -> Role & Responsibility ( 역할과 책임 )
    @PostMapping(
//            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto<List<TodoDto>> createTodo(
            @RequestBody @Valid TodoCreateDto todoDto) {

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
            @RequestBody @Valid TodoUpdateDto todoDto) {
        Todo newTodo = todoMapper.toEntity(todoDto);
        newTodo.setUserId(TEMP_USER_ID);
        todoService.update(newTodo);
        return getTodoList();
    }

    @DeleteMapping
    public ResponseDto<List<TodoDto>> deleteTodo(
            @RequestBody @Valid TodoDeleteDto todoDto) {
//        Todo todo = Todo.from(todoDto);
//        Todo build = Todo.builder().id(todoDto.getId()).userId(TEMP_USER_ID).build();
//        todoService.delete(todo);
        Todo todo = todoMapper.toEntity(todoDto);
        todo.setUserId(TEMP_USER_ID);
        todoService.delete(todo);
        return getTodoList();
    }
}
