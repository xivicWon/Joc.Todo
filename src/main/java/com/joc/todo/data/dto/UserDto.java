package com.joc.todo.data.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

//@ToString(exclude = {"password"})
@ToString
@Builder
@Getter
@Slf4j
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED) //기본생성자 생성을 해주는 애너테이션
public class UserDto {
    private String id;
    private String username;
    private String email;
    private String password;
    private String token;
    // 생성자를 정의하지 않으면 기본생성자가 자동으로 생성된다.

//    @Override
//    public String toString() {
//        return "id: " + id +
//                ",username: " + username +
//                ",email: " + email +
//                ",password: " + password +
//                ",token: " + token;
//    }
}
