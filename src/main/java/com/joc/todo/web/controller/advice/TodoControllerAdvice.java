package com.joc.todo.web.controller.advice;

import com.joc.todo.data.dto.response.ResponseDto;
import com.joc.todo.data.dto.response.ResponseErrorDto;
import com.joc.todo.data.type.ResponseCode;
import com.joc.todo.exception.AuthenticationProblemException;
import com.joc.todo.exception.LoginFailException;
import lombok.extern.slf4j.Slf4j;
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

    @ExceptionHandler({LoginFailException.class, AuthenticationProblemException.class})
    public ResponseEntity<?> authenticationProblemExceptionHandler(RuntimeException e) {
        log.error("AuthenticationProblemException -> {} {}", e.getClass().getSimpleName(), e.getMessage());

        ResponseErrorDto responseErrorDto = new ResponseErrorDto("", "", e.getMessage());
        return ResponseDto.responseEntityOf(ResponseCode.UNAUTHORIZED, responseErrorDto);

    }


    @ExceptionHandler
    public ResponseEntity<?> globalExceptionHandler(Exception e) {
        log.error("applicationExceptionHandler -> ", e);
        ResponseErrorDto responseErrorDto = new ResponseErrorDto("", "", e.getMessage());
        return ResponseDto.responseEntityOf(ResponseCode.INTERNAL_SERVER_ERROR, responseErrorDto);
    }
}
