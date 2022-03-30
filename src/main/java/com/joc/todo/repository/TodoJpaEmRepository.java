package com.joc.todo.repository;

import com.joc.todo.entity.Todo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TodoJpaEmRepository implements TodoRepository {

    private final EntityManager entityManager;

    @Override
    public List<Todo> findAll() {
        String jpql = "select t from Todo t ";
        return entityManager.createQuery(jpql, Todo.class)
                .getResultList();
    }

    @Override
    public List<Todo> findByUserId(String userId) {
        String jpql = "select t from Todo t where t.userId = :userId";
        return entityManager.createQuery(jpql, Todo.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public Optional<Todo> findById(Integer id) {
        String jpql = "select t from Todo t where t.id = :id";
        try{
            Todo resultTodo = entityManager.createQuery(jpql, Todo.class)
                    .setParameter("id", id)
                    .getSingleResult();
            return Optional.of(resultTodo);
        } catch ( NoResultException ex){
            return Optional.empty();
        }
    }

    @Override
    public void save(Todo todo) {
        entityManager.persist(todo);
    }

    @Override
    public void delete(Todo todo) {

    }

    @Override
    public void deleteById(Integer id) {

    }
}
