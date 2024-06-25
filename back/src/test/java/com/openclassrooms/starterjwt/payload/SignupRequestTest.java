package com.openclassrooms.starterjwt.payload;

import com.openclassrooms.starterjwt.payload.request.SignupRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SignupRequestTest {
    private SignupRequest signupRequest;

    @BeforeEach
    void setUp() {
        signupRequest = new SignupRequest();
    }

    @Test
    void shouldSetAndGetEmail() {
        String email = "test@example.com";
        signupRequest.setEmail(email);
        assertEquals(email, signupRequest.getEmail());
    }

    @Test
    void shouldSetAndGetFirstName() {
        String firstName = "John";
        signupRequest.setFirstName(firstName);
        assertEquals(firstName, signupRequest.getFirstName());
    }

    @Test
    void shouldSetAndGetLastName() {
        String lastName = "Doe";
        signupRequest.setLastName(lastName);
        assertEquals(lastName, signupRequest.getLastName());
    }

    @Test
    void shouldSetAndGetPassword() {
        String password = "password";
        signupRequest.setPassword(password);
        assertEquals(password, signupRequest.getPassword());
    }
}
