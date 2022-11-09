package com.joc.todo.web.controller;


import com.joc.todo.data.dto.UserDto;
import com.joc.todo.data.dto.response.ResponseDto;
import com.joc.todo.data.dto.response.ResponseResultDto;
import com.joc.todo.data.entity.User;
import com.joc.todo.service.UserService;
import com.joc.todo.web.auth.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController // Controller ResponseBody
@RequiredArgsConstructor
@RequestMapping("/auth/v4")
public class UserV4Controller {

    private final UserService userService;
    private final TokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseDto<UserDto> logIn(
            @RequestBody UserDto userDto,
            HttpServletRequest request
    ) {
        String email = userDto.getEmail();
        String password = userDto.getPassword();

        User user = userService.logIn(email, password);
        String authToken = tokenProvider.create(user);
        UserDto responseUserDto = UserDto.builder().token(authToken).build();
        return ResponseDto.of(ResponseResultDto.of(responseUserDto));
    }
}
