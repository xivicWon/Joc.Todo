package com.joc.todo.repository;

import com.joc.todo.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserJpaEmRepository implements UserRepository {

    private final EntityManager em;

    @Override
    public void save(User user) {
        em.persist(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        String jpql = "select u from User u where u.email = :email";
        try {
            User user = em.createQuery(jpql, User.class)
                    .setParameter("email", email)
                    .getSingleResult();
            return Optional.ofNullable(user);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Boolean existsByEmail(String email) {
        String qlString = " select 1 from User u where email = :email";
        try {
            Integer cnt = em.createQuery(qlString, Integer.class)
                    .setParameter("email", email)
                    .getSingleResult();
            return cnt != null;
        } catch (NoResultException e) {
            return false;
        }
    }

    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) {

        String jpql = "select u from User u where u.email = :email and password = :password";
        try {
            User user = em.createQuery(jpql, User.class)
                    .setParameter("email", email)
                    .setParameter("password", password)
                    .getSingleResult();
            
            return Optional.ofNullable(user);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
