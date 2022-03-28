package com.joc.todo.repository;

import com.joc.todo.entity.Todo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TodoJpaEmRepository implements TodoRepository{

    private final EntityManager entityManager;

    @Override
    public List<Todo> findAll() {
        String jpql = "select t from Todo t "; // Java Persistance Query Language
        return entityManager.createQuery(jpql, Todo.class)
                .getResultList();
    }
}
