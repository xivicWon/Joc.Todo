package com.joc.todo.web.controller;

import com.joc.todo.data.dto.TodoDto;
import com.joc.todo.data.dto.response.ResponseDto;
import com.joc.todo.data.dto.response.ResponseResultDto;
import com.joc.todo.data.entity.Todo;
import com.joc.todo.data.entity.User;
import com.joc.todo.data.mapper.TodoMapper;
import com.joc.todo.exception.InvalidUserProblemException;
import com.joc.todo.exception.RequiredAuthenticationException;
import com.joc.todo.service.TodoService;
import com.joc.todo.service.UserService;
import com.joc.todo.web.controller.session.SessionConst;
import com.joc.todo.web.controller.session.SessionManager;
import com.joc.todo.web.controller.validation.marker.TodoValidationGroup;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
    private final UserService userService;
    private final TodoMapper todoMapper;
    // http method : GET POST PUT PATCH DELETE OPTIONS HEAD TRACE CONNECT
    private final SessionManager sessionManager;


    @GetMapping("/v1")
    public ResponseDto<List<TodoDto>> getTodoList(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String userId = Arrays.stream(Optional.ofNullable(cookies).orElse(new Cookie[]{}))
                .filter(cookie -> cookie.getName().equals("userId"))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);
        log.info(" cookie[0].userId => {}", userId);

        return getRealTodoList(userId);
    }

    @GetMapping("/v2")
    public ResponseDto<List<TodoDto>> getTodoListV2(@CookieValue(name = "userId", required = false) String userId) {
        log.info(" cookie[0].userId => {}", userId);
        validUserId(userId);
        return getRealTodoList(userId);
    }


    @GetMapping("/v3")
    public ResponseDto<List<TodoDto>> getTodoListV3(HttpServletRequest request) {
        User session = (User) sessionManager.getSession(request);
        validUser(session);
        return getRealTodoList(session.getId());
    }

    @GetMapping("/v4")
    public ResponseDto<List<TodoDto>> getTodoListV4(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SessionConst.SESSION_USER);
        validUser(user);
        return getRealTodoList(user.getId());
    }

    @GetMapping("/v5")
    public ResponseDto<List<TodoDto>> getTodoListV4(HttpSession session) {
        User user = (User) session.getAttribute(SessionConst.SESSION_USER);
        validUser(user);
        return getRealTodoList(user.getId());
    }

    @GetMapping("/v6")
    public ResponseDto<List<TodoDto>> getTodoListV4(@SessionAttribute(name = SessionConst.SESSION_USER, required = false) User loginUser) {
        validUser(loginUser);
        return getRealTodoList(loginUser.getId());
    }

    private ResponseDto<List<TodoDto>> getRealTodoList(String userId) {
        ResponseResultDto<List<TodoDto>> responseResultDto = ResponseResultDto.of(
                todoMapper.toDtoList(todoService.getList(userId))
        );
        return ResponseDto.of(responseResultDto);

    }


    // R&R -> Role & Responsibility ( 역할과 책임 )
    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto<List<TodoDto>> createTodo(
            @RequestBody @Validated({TodoValidationGroup.Creation.class}) TodoDto todoDto,
            @CookieValue(name = "userId", required = false) String userId) {

        validUserId(userId);
        log.info("MY_INFO > todoDto : {}", todoDto);
        Todo todo = todoMapper.toEntity(todoDto);
        todo.setUserId(userId);
        todoService.create(todo);
        return getRealTodoList(userId);
    }

    private void validUserId(String userId) {
        if (!StringUtils.hasText(userId))
            throwNoLogException();

        if (!userService.existsUser(userId))
            throw new InvalidUserProblemException("유효한 회원이 아닙니다.");
    }

    private void throwNoLogException() {
        throw new RequiredAuthenticationException("로그인을 하셔야 합니다.");
    }

    private void validUser(User user) {
        if (null == user) {
            throwNoLogException();
        }
        validUserId(user.getId());
    }


    @PutMapping
    public ResponseDto<List<TodoDto>> updateTodo(
            @RequestBody @Validated({TodoValidationGroup.Update.class}) TodoDto todoDto,
            @CookieValue(name = "userId", required = false) String userId) {

        validUserId(userId);
        Todo newTodo = todoMapper.toEntity(todoDto);
        newTodo.setUserId(userId);
        todoService.update(newTodo);
        return getRealTodoList(userId);
    }

    @DeleteMapping
    public ResponseDto<List<TodoDto>> deleteTodo(
            @RequestBody @Validated({TodoValidationGroup.Deletion.class}) TodoDto todoDto,
            @CookieValue(name = "userId", required = false) String userId) {
        validUserId(userId);
        Todo todo = todoMapper.toEntity(todoDto);
        todo.setUserId(userId);
        todoService.delete(todo);
        return getRealTodoList(userId);
    }
}
