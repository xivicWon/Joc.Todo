package com.joc.todo.repository;

import com.joc.todo.entity.Todo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
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
        log.info("DB에 flush를 하시요 (findByUserId)");
        String jpql = "select t from Todo t where t.userId = :userId";
        return entityManager.createQuery(jpql, Todo.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public Optional<Todo> findById(Integer id) {
        log.info("DB를 flush 하시오 (findById)");
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
        deleteById(todo.getId());
    }

    @Override
    public void deleteById(Integer id) {
        findById(id).ifPresent(entityManager::remove);
    }

    @Override
    public void truncate() {
        String jpql = "truncate table Todo";
        entityManager.createQuery(jpql, Todo.class).executeUpdate();
    }
}
