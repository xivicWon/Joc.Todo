package com.joc.todo.data.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.Collection;

@Getter
public class ResponseResultDto<T> {
    private final T data;
    private final int dataCount;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ResponseResultPageDto pageDto;

    private ResponseResultDto(T data, int dataCount, ResponseResultPageDto pageDto) {
        this.data = data;
        this.dataCount = dataCount;
        this.pageDto = pageDto;
    }


    private ResponseResultDto(T data, int dataCount) {
        this.data = data;
        this.dataCount = dataCount;
    }

    public static <T> ResponseResultDto<T> of(T data, int dataCount, ResponseResultPageDto pageDto) {
        return new ResponseResultDto<>(data, dataCount, pageDto);
    }


    public static <T> ResponseResultDto<T> of(T data, int dataCount) {
        return new ResponseResultDto<>(data, dataCount);
    }

    public static <T> ResponseResultDto<T> of(T data) {
        return new ResponseResultDto<>(data, sizeof(data));
    }

    private static <T> int sizeof(T data) {
        return (data instanceof Collection) ? ((Collection<?>) data).size() : 1;
    }


}
