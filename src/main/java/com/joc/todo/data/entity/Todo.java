package com.joc.todo.data.entity;

import com.joc.todo.data.dto.TodoDto;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

// JPA => Java의 표준 ORM 이다.
// Hibernate 가 기본적인 구현체다.
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@Setter
@ToString
public class Todo {

    private static final String TEMP_USER_ID = "temp";
    @Id
    @GeneratedValue
    private Integer id;
    private String userId;
    private String title;
    private LocalDateTime createAt;
    private boolean done;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public static Todo from(TodoDto dto) {
        return Todo.builder()
                .id(dto.getId())
                .userId(TEMP_USER_ID)
                .title(dto.getTitle())
                .done(dto.isDone())
                .build();
    }

}
