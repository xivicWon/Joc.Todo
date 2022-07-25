package com.joc.todo.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "member")
@Builder
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // JPA spec
@AllArgsConstructor(access = AccessLevel.PRIVATE)   // Builder
public class User {

    // JPA 와 hibernate
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;
    private String username;
    private String email;
    private String password;


}
