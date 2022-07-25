package com.joc.todo.service;

import com.joc.todo.entity.User;
import com.joc.todo.exception.ApplicationException;
import com.joc.todo.exception.LoginFailException;
import com.joc.todo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public void signUp(User user) {
        if (user == null || user.getEmail() == null) {
            log.error("Invalid user : {}", user);
            throw new ApplicationException("Invalid user");
        }

        if (Boolean.TRUE.equals(userRepository.existsByEmail(user.getEmail()))) {
            log.error("Email already exist {}", user.getEmail());
            throw new ApplicationException("Email already exist");
        }
        userRepository.save(user);
    }

    public User logIn(String email, String password) {
        if (!StringUtils.hasText(email)) {
            log.error("Email is Null or Blank {}", email);
            throw new ApplicationException("Email already exist");
        }

        if (!StringUtils.hasText(password)) {
            log.error("Password is Null or Blank {}", password);
            throw new ApplicationException("Password is Null or Blank");
        }

        Optional<User> user = userRepository.findByEmailAndPassword(email, password);
        return user.orElseThrow(() -> new LoginFailException("아이디 또는 패스워드가 잘못되었습니다."));
    }
}

