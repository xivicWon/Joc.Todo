package com.joc.todo.dto;


import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@NoArgsConstructor(access = AccessLevel.PUBLIC)  // HttpMessageConverter를 위해 필요.
@AllArgsConstructor()
@Getter
@ToString
public class TodoCreateDto {

    @NotNull
    @NotEmpty
    @NotBlank
    private String title;

}
