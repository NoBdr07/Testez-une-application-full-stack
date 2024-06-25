package com.openclassrooms.starterjwt.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TeacherTest {
    private Teacher teacher;

    @BeforeEach
    void setUp() {
        teacher = new Teacher();
    }

    @Test
    void shouldSetAndGetId() {
        Long id = 1L;
        teacher.setId(id);
        assertEquals(id, teacher.getId());
    }

    @Test
    void shouldSetAndGetLastName() {
        String lastName = "Doe";
        teacher.setLastName(lastName);
        assertEquals(lastName, teacher.getLastName());
    }

    @Test
    void shouldSetAndGetFirstName() {
        String firstName = "John";
        teacher.setFirstName(firstName);
        assertEquals(firstName, teacher.getFirstName());
    }

    @Test
    void shouldSetAndGetCreatedAt() {
        LocalDateTime createdAt = LocalDateTime.now();
        teacher.setCreatedAt(createdAt);
        assertEquals(createdAt, teacher.getCreatedAt());
    }

    @Test
    void shouldSetAndGetUpdatedAt() {
        LocalDateTime updatedAt = LocalDateTime.now();
        teacher.setUpdatedAt(updatedAt);
        assertEquals(updatedAt, teacher.getUpdatedAt());
    }
}