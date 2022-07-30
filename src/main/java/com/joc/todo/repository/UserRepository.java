package com.joc.todo.repository;

import com.joc.todo.entity.User;

import java.util.Optional;

public interface UserRepository {

    void save(User user);

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);

    Optional<User> findByEmailAndPassword(String email, String password);


    Optional<User> findById(String userId);
}

