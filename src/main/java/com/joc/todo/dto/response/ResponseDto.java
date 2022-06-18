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


    private List<String> errors;

    private ResponseDto(String code, String message, ResponseResultDto<T> result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }

    private ResponseDto(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseDto(String code, String message, String errors) {
        this.code = code;
        this.message = message;
        this.addError(errors);
    }

    // 정적 팩토리 메서드의 명명 방식 관례 ( from, of, valueOf, getInstance, instance, create, newInstance, getType,
    //    public static <T> ResponseDto<T> of(T res) {
    //        return new ResponseDto<>(SUCCESS_CODE, SUCCESS_MESSAGE, res);
    //    }
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

    public static <T> ResponseEntity<ResponseDto<T>> responseEntityOf(ResponseCode responseCode, String message) {
        return ResponseEntity
                .status(responseCode.getHttpStatus())
                .body(
                        new ResponseDto<>(responseCode.code(), responseCode.getMessage(), message)
                );
    }

    public static <T> ResponseDto<T> of(ResponseCode responseCode) {
        return new ResponseDto<>(responseCode.code(), responseCode.getMessage());
    }


    public void addError(String message) {
        if (this.errors == null) {
            this.errors = new ArrayList<>();
        }
        this.errors.add(message);
    }
}
