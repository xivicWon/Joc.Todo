package com.joc.todo.data.dto.response;

import lombok.Getter;

@Getter
public class ResponseError {

    private String message;


    public ResponseError(String message) {
        this.message = message;
    }
}
