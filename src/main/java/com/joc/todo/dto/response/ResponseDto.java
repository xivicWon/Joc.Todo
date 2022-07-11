package com.joc.todo.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.joc.todo.type.ResponseCode;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Generic Character
 * E - Element
 * N - Number
 * T - Type
 * K - Key
 * V - Value
 */

// 불변(immutable) 객체로 만드는 것이 좋다. -> setter method 가 없음.

@Getter // Json Serialize 할때 데이터를 조회하기위해 필요함.
// 정적 팩토리 메서드를 막기 위해 private 로 선언
//@AllArgsConstructor(access = AccessLevel.PRIVATE)

public class ResponseDto<T> {
    private final String code;
    private final String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ResponseResultDto<T> result; // 연관관계( Association ) => 집약관계 ( aggregation )

    @JsonInclude(JsonInclude.Include.NON_NULL)

    private List<ResponseErrorDto> errors;

    private ResponseDto(String code, String message, ResponseResultDto<T> result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }

    private ResponseDto(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseDto(String code, String message, ResponseErrorDto error) {
        this.code = code;
        this.message = message;
        this.addError(error);
    }

    public ResponseDto(String code, String message, List<ResponseErrorDto> errors) {
        this.code = code;
        this.message = message;
        this.errors = errors;
    }

    public static <T> ResponseDto<T> of(ResponseResultDto<T> result) {
        return new ResponseDto<>(ResponseCode.SUCCESS.code(), ResponseCode.SUCCESS.getMessage(), result);
    }

    public static <T> ResponseEntity<ResponseDto<T>> responseEntityOf(ResponseResultDto<T> result) {
        return ResponseEntity
                .ok()
                .body(
                        new ResponseDto<>(ResponseCode.SUCCESS.code(), ResponseCode.SUCCESS.getMessage(), result)
                );
    }

    public static <T> ResponseEntity<ResponseDto<T>> responseEntityOf(ResponseCode responseCode) {
        return ResponseEntity
                .status(responseCode.getHttpStatus())
                .body(
                        new ResponseDto<>(responseCode.code(), responseCode.getMessage())
                );
    }

    public static <T> ResponseEntity<ResponseDto<T>> responseEntityOf(ResponseCode responseCode, ResponseResultDto<T> result) {
        return ResponseEntity
                .status(responseCode.getHttpStatus())
                .body(
                        new ResponseDto<>(responseCode.code(), responseCode.getMessage(), result)
                );
    }

    public static <T> ResponseEntity<ResponseDto<T>> responseEntityOf(ResponseCode responseCode, ResponseErrorDto message) {
        return ResponseEntity
                .status(responseCode.getHttpStatus())
                .body(
                        new ResponseDto<>(responseCode.code(), responseCode.getMessage(), message)
                );
    }

    public static <T> ResponseEntity<ResponseDto<T>> responseEntityOf(ResponseCode responseCode, List<ResponseErrorDto> errors) {
        return ResponseEntity
                .status(responseCode.getHttpStatus())
                .body(
                        new ResponseDto<>(responseCode.code(), responseCode.getMessage(), errors)
                );
    }

    public static <T> ResponseDto<T> of(ResponseCode responseCode) {
        return new ResponseDto<>(responseCode.code(), responseCode.getMessage());
    }


    public void addError(ResponseErrorDto error) {
        if (this.errors == null) {
            this.errors = new ArrayList<>();
        }
        this.errors.add(error);
    }
}
