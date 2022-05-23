package com.joc.todo.service;

import com.joc.todo.entity.User;
import com.joc.todo.exception.ApplicationException;
import com.joc.todo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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

    User createUser(String email, String password) {
        String name = "나이키아디다스";
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
        assertThatThrownBy(() -> service.signUp(user))
                .isInstanceOf(ApplicationException.class);

    }

    @Test
    void signUp_emailisNull() {
        //given
        String email = null;
        User user = createUser(email);

        //when
        //then
        assertThatThrownBy(() -> service.signUp(user))
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
        assertThatThrownBy(() -> service.signUp(user))
                .isInstanceOf(ApplicationException.class);
    }

    //  숙제 2022.05.21
    //  logIn 테스트 코드 작성하기
    //
    @Test
    void logIn() {
        //given
        String email = "test@mail.com";
        String password = "1234";
        User user = createUser(email, password); // Test Driven Development 방식 ( 미리 만들어놓고 원형함수를 만드는 방식 => 역순 )
        doReturn(Optional.of(user))
                .when(userRepository)
                .findByEmailAndPassword(email, password);

        //when
        User result = service.logIn(email, password);

        //then
        assertThat(result).isEqualTo(user);
    }

    @ParameterizedTest(name = "[{index}] \"{0}\" is blank")
    @NullAndEmptySource
    @ValueSource(strings = {" ", "    ", "\n", "\r", "\t"})
    @DisplayName("로그인시 이메일이 없는경우")
    void logIn_noEmail(String email) {
        //given
        String password = "1234";

        //when && then
        assertThatThrownBy(() -> service.logIn(email, password))
                .isInstanceOf(ApplicationException.class);
    }

    @ParameterizedTest(name = "[{index}] \"{0}\" is blank")
    @NullAndEmptySource
    @ValueSource(strings = {" ", "    ", "\n", "\r", "\t"})
    @DisplayName("로그인시 비밀번호가 없는경우")
    void logIn_noPassword(String password) {
        //given
        String email = "test@mail.com";

        //when && then
        assertThatThrownBy(() -> service.logIn(email, password))
                .isInstanceOf(ApplicationException.class);
    }


    @Test
    @DisplayName("로그인시 결과값이 없는경우")
    void logIn_noReturnData() {
        // given
        String email = "test@mail.com";
        String password = "1234";
        User user = createUser(email, password);

        doReturn(Optional.empty())
                .when(userRepository)
                .findByEmailAndPassword(email, password);

        // when
        User result = service.logIn(email, password);
        // then
        assertThat(result).isNull();
    }
}