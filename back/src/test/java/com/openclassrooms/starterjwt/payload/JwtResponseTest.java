package com.openclassrooms.starterjwt.payload;

import com.openclassrooms.starterjwt.payload.response.JwtResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtResponseTest {
    private JwtResponse jwtResponse;

    @BeforeEach
    void setUp() {
        jwtResponse = new JwtResponse("token", 1L, "username", "firstName", "lastName", true);
    }

    @Test
    void shouldSetAndGetToken() {
        String token = "newToken";
        jwtResponse.setToken(token);
        assertEquals(token, jwtResponse.getToken());
    }

    @Test
    void shouldSetAndGetType() {
        String type = "newType";
        jwtResponse.setType(type);
        assertEquals(type, jwtResponse.getType());
    }

    @Test
    void shouldSetAndGetId() {
        Long id = 2L;
        jwtResponse.setId(id);
        assertEquals(id, jwtResponse.getId());
    }

    @Test
    void shouldSetAndGetUsername() {
        String username = "newUsername";
        jwtResponse.setUsername(username);
        assertEquals(username, jwtResponse.getUsername());
    }

    @Test
    void shouldSetAndGetFirstName() {
        String firstName = "newFirstName";
        jwtResponse.setFirstName(firstName);
        assertEquals(firstName, jwtResponse.getFirstName());
    }

    @Test
    void shouldSetAndGetLastName() {
        String lastName = "newLastName";
        jwtResponse.setLastName(lastName);
        assertEquals(lastName, jwtResponse.getLastName());
    }

    @Test
    void shouldSetAndGetAdminStatus() {
        jwtResponse.setAdmin(false);
        assertFalse(jwtResponse.getAdmin());
    }
}
