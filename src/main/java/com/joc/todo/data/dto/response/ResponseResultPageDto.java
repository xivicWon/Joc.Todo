package com.joc.todo.data.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseResultPageDto {

    private int page;
    private int rowSize;
    private int pageCount;

    public static ResponseResultPageDto of(int page, int rowSize, int pageCount) {
        return new ResponseResultPageDto(page, rowSize, pageCount);
    }
}
