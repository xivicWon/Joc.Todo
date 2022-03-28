package com.joc.todo.repository;

import com.joc.todo.entity.Todo;

import java.util.List;

public interface TodoRepository {


    List<Todo> findAll();



}
