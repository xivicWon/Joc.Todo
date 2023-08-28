package com.testcode.apartment.service;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.PostgreSQLContainer;

class MemberServiceManualTest extends MemberServiceSpringBootTest {

    static PostgreSQLContainer<?> POSTGRES_CONTAINER;


    @BeforeAll
    static void setUp() {
        POSTGRES_CONTAINER = new PostgreSQLContainer<>("postgres:13")
                .withInitScript("member-init.sql");
        POSTGRES_CONTAINER.start();

        System.setProperty("spring.datasource.url", POSTGRES_CONTAINER.getJdbcUrl());
        System.setProperty("spring.datasource.username", POSTGRES_CONTAINER.getUsername());
        System.setProperty("spring.datasource.password", POSTGRES_CONTAINER.getPassword());
    }

    @AfterAll
    static void tearDown() {
        // 적지 않아도 시간지나면 자동 가비지컬렉한다.
        POSTGRES_CONTAINER.stop();
    }

}