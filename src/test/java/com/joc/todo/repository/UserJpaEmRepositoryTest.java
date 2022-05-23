package com.joc.todo.repository;

import com.joc.todo.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@Slf4j
@SpringBootTest
@Transactional
class UserJpaEmRepositoryTest {

    @Autowired
    UserRepository repository;


    User createUser() {

        String email = "nike@naike.com";
        String name = "나이키아디다스";
        String password = "1234";
        return User.builder()
                .username(name)
                .email(email)
                .password(password)
                .build();
    }

    @Test
    @DisplayName("기본테스트.")
    void basic() {
        //Given
        User user = createUser();
        //When
        repository.save(user);

        Boolean exists = repository.existsByEmail(user.getEmail());
        Optional<User> byEmail = repository.findByEmail(user.getEmail());
        Optional<User> byEmailPassword = repository.findByEmailAndPassword(user.getEmail(), user.getPassword());

        //Then
//        assertThat(byEmail).isPresent();
//        assertThat(byEmail.get()).isEqualTo(user);
//        assertThat(byEmail.get()).isSameAs(user);
        assertThat(exists).isTrue();

        assertThat(byEmail)
                .isPresent()
                .contains(user)
                .containsSame(user);

        assertThat(byEmailPassword)
                .isPresent()
                .contains(user)
                .containsSame(user);

    }

}