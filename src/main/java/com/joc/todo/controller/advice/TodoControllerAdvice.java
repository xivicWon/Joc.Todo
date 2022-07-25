package com.joc.todo.controller.advice;

import com.joc.todo.dto.response.ResponseDto;
import com.joc.todo.dto.response.ResponseErrorDto;
import com.joc.todo.exception.LoginFailException;
import com.joc.todo.type.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class TodoControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<?> applicationExceptionHandler(BindException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        List<ResponseErrorDto> errors = fieldErrors.stream()
                .map(fieldError -> new ResponseErrorDto(fieldError.getField(), fieldError.getRejectedValue(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());
        return ResponseDto.<String>responseEntityOf(ResponseCode.BAD_REQUEST, errors);
    }

    @ExceptionHandler
    public ResponseEntity<?> globalExceptionHandler(Exception e) {
        log.error("applicationExceptionHandler -> ", e);
        return ResponseEntity.internalServerError().body("ERROR");
    }

    @ExceptionHandler
    public ResponseEntity<?> loginFailExceptionHandler(LoginFailException e) {
        log.error("LoginFailException -> {} {}", e.getClass().getSimpleName(), e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("ERROR");
    }
}
