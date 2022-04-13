package com.joc.todo.repository;

import com.joc.todo.entity.Todo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TodoRepository {


    List<Todo> findAll();

    List<Todo> findByUserId(String userId);

    Optional<Todo> findById(Integer id);

    void save(Todo todo);

    void delete(Todo todo );

    void deleteById(Integer id);

    void truncate();

}
