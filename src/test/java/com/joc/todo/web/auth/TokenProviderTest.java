package com.joc.todo.web.auth;

import com.joc.todo.data.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class TokenProviderTest {

    TokenProvider tokenProvider = new TokenProvider();
    static String TEST_USER_ID = "setset";
    static String token = "";

    @Test
    @Order(1)
    void create() {
        User user = User.builder()
                .id(TEST_USER_ID)
                .email("23232@mobobo.com")
                .build();

        token = tokenProvider.create(user);
        System.out.println(token);

        // then
        Assertions.assertThat(token).isNotEmpty();
    }

    @Test
    @Order(2)
    void validateAndGetUserId() {

        create();

        String userId = tokenProvider.validateAndGetUserId(token);

        Assertions.assertThat(userId).isEqualTo(TEST_USER_ID);


    }
}