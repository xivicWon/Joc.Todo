package com.joc.todo.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

// JPA => Java의 표준 ORM 이다.
// Hibernate 가 기본적인 구현체다.
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter @Setter
@ToString
public class Todo {

    @Id @GeneratedValue
    private Integer id;
    private String userId;
    private String title;
    private boolean done;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


}
