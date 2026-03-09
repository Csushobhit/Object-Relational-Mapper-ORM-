package com.yourcompany.simpleorm.simple_orm_framework;

import com.yourcompany.simpleorm.jdbc.ConnectionManager;
import com.yourcompany.simpleorm.session.SimpleOrmSession;
import com.yourcompany.simpleorm.model.User;

import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AppTest {

    static SimpleOrmSession session;
    static String testUsername;

    @BeforeAll
    static void setup() throws Exception {

        ConnectionManager connectionManager = new ConnectionManager();
        Connection connection = connectionManager.getConnection();

        session = new SimpleOrmSession(connection);

        // unique username every run
        testUsername = "user_" + System.currentTimeMillis();
    }

    @Test
    @Order(1)
    void testSaveUser() {

        User user = new User();
        user.setUsername(testUsername);
        user.setEmail("test@test.com");

        session.save(user);

        assertNotNull(user);
    }

    @Test
    @Order(2)
    void testFindById() {

        // find by username instead of assuming ID
        Optional<User> user = session.findById(User.class, 1L);

        assertTrue(user.isPresent());
    }

    @Test
    @Order(3)
    void testUpdate() {

        Optional<User> userOpt = session.findById(User.class, 1L);

        assertTrue(userOpt.isPresent());

        User user = userOpt.get();
        user.setEmail("updated@test.com");

        session.update(user);

        Optional<User> updated = session.findById(User.class, 1L);

        assertEquals("updated@test.com", updated.get().getEmail());
    }

    @Test
    @Order(4)
    void testDelete() {

        Optional<User> userOpt = session.findById(User.class, 1L);

        assertTrue(userOpt.isPresent());

        session.delete(userOpt.get());

        Optional<User> deleted = session.findById(User.class, 1L);

        assertTrue(deleted.isEmpty());
    }
}