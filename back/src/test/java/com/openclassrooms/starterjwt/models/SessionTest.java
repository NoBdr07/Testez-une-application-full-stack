package com.openclassrooms.starterjwt.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SessionTest {
    private Session session;

    @BeforeEach
    void setUp() {
        session = new Session();
    }

    @Test
    void shouldSetAndGetId() {
        Long id = 1L;
        session.setId(id);
        assertEquals(id, session.getId());
    }

    @Test
    void shouldSetAndGetName() {
        String name = "Test Session";
        session.setName(name);
        assertEquals(name, session.getName());
    }

    @Test
    void shouldSetAndGetDate() {
        Date date = new Date();
        session.setDate(date);
        assertEquals(date, session.getDate());
    }

    @Test
    void shouldSetAndGetDescription() {
        String description = "Test Description";
        session.setDescription(description);
        assertEquals(description, session.getDescription());
    }

    @Test
    void shouldSetAndGetTeacher() {
        Teacher teacher = Mockito.mock(Teacher.class);
        session.setTeacher(teacher);
        assertEquals(teacher, session.getTeacher());
    }

    @Test
    void shouldSetAndGetUsers() {
        User user1 = Mockito.mock(User.class);
        User user2 = Mockito.mock(User.class);
        List<User> users = Arrays.asList(user1, user2);
        session.setUsers(users);
        assertEquals(users, session.getUsers());
    }

    @Test
    void shouldSetAndGetCreatedAt() {
        LocalDateTime createdAt = LocalDateTime.now();
        session.setCreatedAt(createdAt);
        assertEquals(createdAt, session.getCreatedAt());
    }

    @Test
    void shouldSetAndGetUpdatedAt() {
        LocalDateTime updatedAt = LocalDateTime.now();
        session.setUpdatedAt(updatedAt);
        assertEquals(updatedAt, session.getUpdatedAt());
    }
}
