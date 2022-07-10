package com.joc.todo.controller.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class TodoControllerAdvice {


    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> badRequestHandler(Exception e) {
        return ResponseEntity.internalServerError().body("ERROR");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> globalExceptionHandler(Exception e) {
        return ResponseEntity.badRequest().body("ERROR");
    }
//
//    @ExceptionHandler()
//    public ResponseEntity<?> exceptionHandler2(Exception e) {
//        return ResponseDto.responseEntityOf(ResponseCode.BAD_REQUEST);
//    }

}
