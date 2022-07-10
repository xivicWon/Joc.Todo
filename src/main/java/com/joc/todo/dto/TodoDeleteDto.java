package com.joc.todo.dto;


import lombok.*;

import javax.validation.constraints.NotNull;

@NoArgsConstructor(access = AccessLevel.PUBLIC)  // HttpMessageConverter를 위해 필요.
@AllArgsConstructor()
@Getter
@ToString
public class TodoDeleteDto {

    @NotNull
    private Integer id;
}