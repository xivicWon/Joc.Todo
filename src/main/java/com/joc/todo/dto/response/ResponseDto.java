package com.joc.todo.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

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
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseDto<T> {
    private static final String SUCCESS_CODE = "0000";
    private static final String SUCCESS_MESSAGE = "성공";

    private String code;
    private String message;
    private ResponseResultDto<T> result; // 연관관계( Association ) => 집약관계 ( aggregation )

    // 정적 팩토리 메서드의 명명 방식 관례 ( from, of, valueOf, getInstance, instance, create, newInstance, getType,
//    public static <T> ResponseDto<T> of(T res) {
//        return new ResponseDto<>(SUCCESS_CODE, SUCCESS_MESSAGE, res);
//    }
    public static <T> ResponseDto<T> of(ResponseResultDto<T> result) {
        return new ResponseDto<>(SUCCESS_CODE, SUCCESS_MESSAGE, result);
    }

//    public static <T> ResponseDto<T> of(String code, String message, T result) {
//        return new ResponseDto<>(code, message, result);
//    }

}
