package com.joc.todo.controller.session;

import com.joc.todo.data.entity.User;
import com.joc.todo.web.controller.session.SessionManager;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;

class SessionManagerTest {

    SessionManager sessionManager = new SessionManager();

    @Test
    void createSession() {
        MockHttpServletResponse response = new MockHttpServletResponse();
        User user = User.builder().build();
        // 생성
        sessionManager.createSession(user, response);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setCookies(response.getCookies());

        // 조회
        Object result = sessionManager.getSession(request);
        assertThat(result).isEqualTo(user);

        // 제거
        sessionManager.expireSession(request);
        Object result2 = sessionManager.getSession(request);
        assertThat(result2).isNull();
    }
}