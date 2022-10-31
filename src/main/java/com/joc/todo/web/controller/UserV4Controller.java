package com.joc.todo.web.controller;


import com.joc.todo.data.dto.UserDto;
import com.joc.todo.data.dto.response.ResponseDto;
import com.joc.todo.data.dto.response.ResponseResultDto;
import com.joc.todo.data.dto.response.ResponseResultPageDto;
import com.joc.todo.data.entity.User;
import com.joc.todo.data.mapper.UserMapper;
import com.joc.todo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
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
    private final UserMapper userMapper;


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


    @PostMapping("/login")
    public ResponseDto<UserDto> logIn(
            @RequestBody UserDto userDto,
            HttpServletRequest request
    ) {
        String email = userDto.getEmail();
        String password = userDto.getPassword();
        User user = userService.logIn(email, password);
        UserDto responseUserDto = UserDto.builder().token(user.getId()).build();
        return ResponseDto.of(ResponseResultDto.of(responseUserDto));

    }


}
