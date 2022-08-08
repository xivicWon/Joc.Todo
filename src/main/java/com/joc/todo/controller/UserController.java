package com.joc.todo.controller;


import com.joc.todo.controller.session.SessionConst;
import com.joc.todo.controller.session.SessionManager;
import com.joc.todo.dto.UserDto;
import com.joc.todo.dto.response.ResponseDto;
import com.joc.todo.dto.response.ResponseResultDto;
import com.joc.todo.dto.response.ResponseResultPageDto;
import com.joc.todo.entity.User;
import com.joc.todo.mapper.UserMapper;
import com.joc.todo.service.UserService;
import com.joc.todo.type.ResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@RestController // Controller ResponseBody
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final SessionManager sessionManager;

    @PostMapping(value = "/signup",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto<UserDto> signUp(
            @RequestBody UserDto userDto        // MappingJackson2HttpMessageConverter : 객체생성(디폴트생성자) ->
            // Setter(x) => (getter/setter 매서드를 이용해 프로퍼티를 찾아서  Reflection )
    ) {
        log.info("user Dto >> {} ", userDto);
        User user = userMapper.toEntity(userDto);
        // 필수 필드에 대한 정책 정리는 서비스 계층에서 해주어야 함.
        // 컨트롤러에서는 따로 정책에 대한 부분이 포함되어선 안된다.

        userService.signUp(user);
        UserDto responseUserDto = userMapper.toDto(user);

        ResponseResultDto<UserDto> responseResultDto = ResponseResultDto.of(
                responseUserDto, 1, ResponseResultPageDto.of(1, 20, 15)
        );
        return ResponseDto.of(responseResultDto);

    }

    @PostMapping("/login/v1")
    public ResponseDto<UserDto> logInV1(
            @RequestBody UserDto userDto,
            HttpServletResponse response
    ) {
        log.debug(">>> login >>  {}  ", userDto);
        String email = userDto.getEmail();
        String password = userDto.getPassword();
        User user = userService.logIn(email, password);
        Cookie userId = new Cookie("userId", user.getId());
        userId.setPath("/");
        response.addCookie(userId);

        return ResponseDto.of(ResponseCode.SUCCESS);
    }

    @PostMapping("/login/v2")
    public ResponseDto<UserDto> logInV2(
            @RequestBody UserDto userDto,
            HttpServletResponse response
    ) {
        String email = userDto.getEmail();
        String password = userDto.getPassword();
        User user = userService.logIn(email, password);
        sessionManager.createSession(user, response);
        return ResponseDto.of(ResponseCode.SUCCESS);
    }

    @PostMapping("/login/v3")
    public ResponseDto<UserDto> logInV3(
            @RequestBody UserDto userDto,
            HttpServletRequest request
    ) {
        String email = userDto.getEmail();
        String password = userDto.getPassword();
        User user = userService.logIn(email, password);

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.SESSION_USER_KEY, user);
        return ResponseDto.of(ResponseCode.SUCCESS);
    }

    @PostMapping("/logout/v1")
    public ResponseDto<UserDto> logOutV1(
            HttpServletResponse response) {
        Cookie cookieUser = new Cookie("userId", "");
        cookieUser.setMaxAge(0);
        cookieUser.setPath("/");
        response.addCookie(cookieUser);
        return ResponseDto.of(ResponseCode.SUCCESS);
    }

    @PostMapping("/logout/v2")
    public ResponseDto<UserDto> logOutV2(
            HttpServletRequest request) {
        sessionManager.expireSession(request);
        return ResponseDto.of(ResponseCode.SUCCESS);
    }

    @PostMapping("/logout/v3")
    public ResponseDto<UserDto> logOutV3(
            HttpServletRequest request) {
        sessionManager.expireSession(request);
        HttpSession session = request.getSession();
        session.invalidate();
        return ResponseDto.of(ResponseCode.SUCCESS);
    }
}
