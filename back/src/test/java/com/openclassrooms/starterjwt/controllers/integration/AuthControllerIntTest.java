package com.openclassrooms.starterjwt.controllers.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.starterjwt.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.openclassrooms.starterjwt.payload.request.LoginRequest;
import com.openclassrooms.starterjwt.payload.request.SignupRequest;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional // to not save what the tests change in db
public class AuthControllerIntTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

     // Test the register request.
     // New user is deleted after the test.
    @Test
    public void testRegister() throws Exception {
        // Create a new user
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("test@test.com");
        signupRequest.setPassword("password");
        signupRequest.setFirstName("test");
        signupRequest.setLastName("TEST");

        String jsonRequest = objectMapper.writeValueAsString(signupRequest);

        // Register the new user
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isOk());
    }

     // Test the login request
     // The user with email yoga@studio.com and password test!1234 must exist in db.
    @Test
    public void testLogin() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("yoga@studio.com");
        loginRequest.setPassword("test!1234");
        String jsonRequest = objectMapper.writeValueAsString(loginRequest);

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isOk());
    }
}
