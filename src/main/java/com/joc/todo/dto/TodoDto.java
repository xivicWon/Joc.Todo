package com.joc.todo.dto;


import com.joc.todo.entity.Todo;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Builder // 다른 생성자가 없으면, 전체 파라미터 생성자 자동생성.
@NoArgsConstructor(access = AccessLevel.PUBLIC)  // HttpMessageConverter를 위해 필요.
@AllArgsConstructor(access = AccessLevel.PRIVATE) // builder 를 위해 필요.
@Getter
@Slf4j
public class TodoDto {

    private Integer id;
    private String title;
    private boolean done;

    // 인스턴스 (Instance ) 메소드
    // 인스턴스 => 객체 ( new 클래스 )
    // 정적 (Static) 메소드
    public static TodoDto toDto(Todo todo) {
        return TodoDto.builder()
                .id(todo.getId())
                .title(todo.getTitle())
                .done(todo.isDone())
                .build();
    }

    public static List<TodoDto> todoDtoList(List<Todo> todoList) {
//        Stream<Todo> stream = todoList.stream();
//        Stream<TodoDto> todoDtoStream = stream.map(TodoDto::toDto);
//        List<TodoDto> collect = todoDtoStream.collect(Collectors.toList());
//        return collect;
        return todoList.stream()
//                .peek(t -> log.info(" todo : {}", t))
                .map(TodoDto::toDto)
//                .peek(t -> log.info(" todoDTO : {}", t))
                .collect(Collectors.toList());
    }
}
