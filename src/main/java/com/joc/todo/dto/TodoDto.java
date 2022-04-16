package com.joc.todo.dto;


import com.joc.todo.entity.Todo;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
@Slf4j
public class TodoDto {

    private Integer id;
    private String title;
    private boolean done;

    // 인스턴스 (Instance ) 메소드 
    //      인스턴스 => 객체 ( new 클래스 ) 
    // 정적 (Static) 메소드
    //      메소드
    public static TodoDto toDto(Todo todo) {
        return TodoDto.builder()
                .id(todo.getId())
                .title(todo.getTitle())
                .done(todo.isDone())
                .build();
    }

    public static List<TodoDto> todoDtoList(List<Todo> todoList) {
        return todoList.stream()
                .parallel()
//                .peek(t -> log.info(" todo : {}", t))
                .map(TodoDto::toDto)
//                .peek(t -> log.info(" todoDTO : {}", t))
                .collect(Collectors.toList());
    }
}
