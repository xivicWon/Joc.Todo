package com.joc.todo.service;

import com.joc.todo.entity.User;
import com.joc.todo.exception.ApplicationException;
import com.joc.todo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    UserService service;

    @Mock   // Fake-double, Mock, Stub
    UserRepository userRepository;

    User createUser(String email) {
        String name = "나이키아디다스";
        String password = "1234";
        return User.builder()
                .username(name)
                .email(email)
                .password(password)
                .build();
    }


    @Test
    void signUp_success() {
        //given
        String email = "test@mail.com";
        User user = createUser(email);

        //when
        service.signUp(user);

        //then
        verify(userRepository, times(1))
                .save(user);
    }

    @Test
    void signUp_fail() {
        //given
        User user = null;

        //when
        //then
        Assertions.assertThatThrownBy(() -> service.signUp(user))
                .isInstanceOf(ApplicationException.class);

    }

    @Test
    void signUp_emailisNull() {
        //given
        String email = null;
        User user = createUser(email);

        //when
        //then
        Assertions.assertThatThrownBy(() -> service.signUp(user))
                .isInstanceOf(ApplicationException.class);

    }

    @Test
    void signUp_emailAlreadyExists() {
        //given
        String email = "test@mail.com";
        User user = createUser(email);

        doReturn(true)
                .when(userRepository)
                .existsByEmail(any());
        //when
        Assertions.assertThatThrownBy(() -> service.signUp(user))
                .isInstanceOf(ApplicationException.class);
    }

    //  숙제 2022.05.21
    //  logIn 테스트 코드 작성하기
    //
    @Test
    void logIn() {
        //given
        //when
        //then
    }

    @Test
    void logOut() {
        //given
        //when
        //then
    }
}