package com.joc.todo.controller;


import com.joc.todo.dto.UserDto;
import com.joc.todo.dto.response.ResponseDto;
import com.joc.todo.dto.response.ResponseResultDto;
import com.joc.todo.dto.response.ResponseResultPageDto;
import com.joc.todo.entity.User;
import com.joc.todo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController // Controller ResponseBody
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/signup",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto<UserDto> signUp(
            @RequestBody UserDto userDto        // MappingJackson2HttpMessageConverter : 객체생성(디폴트생성자) ->
            // Setter(x) => (getter/setter 매서드를 이용해 프로퍼티를 찾아서  Reflection )
    ) {
        log.info("user Dto >> {} ", userDto);

        // 필수 필드에 대한 정책 정리는 서비스 계층에서 해주어야 함.
        // 컨트롤러에서는 따로 정책에 대한 부분이 포함되어선 안된다.
        User user = User.builder()
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .build();

        // Service Layer에 위임
        try {
            userService.signUp(user);
            UserDto responseUserDto = UserDto.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .password(user.getPassword())
                    .build();


            ResponseResultDto<UserDto> responseResultDto = ResponseResultDto.of(responseUserDto, 1, ResponseResultPageDto.of(1, 20, 15));
            return ResponseDto.of(responseResultDto);
        } catch (Exception e) {
            throw e;
        }
    }

    @ExceptionHandler
    public ResponseEntity<?> exceptionHandler(Exception e) {
        return ResponseEntity.internalServerError().body("ERROR");
    }

    @PostMapping("/login")
    public ResponseDto<UserDto> logIn(
            @RequestBody UserDto userDto
    ) {
        log.debug(">>> login >>  {}  ", userDto);
        String email = userDto.getEmail();
        String password = userDto.getPassword();
        User user = userService.logIn(email, password);

        var responseUserDto = UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .build();

        // 타입 추론( Type Inference )
        ResponseResultDto<UserDto> responseResultDto = ResponseResultDto.of(responseUserDto);
        return ResponseDto.of(responseResultDto);
    }
}