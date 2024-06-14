package com.openclassrooms.starterjwt.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.mapper.SessionMapper;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.services.SessionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@AutoConfigureMockMvc
public class SessionControllerIntTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private SessionMapper sessionMapper;

    @Test
    @WithMockUser(roles = "ADMIN")
    public void findByIdTest() throws Exception {
        mockMvc.perform(get("/api/session/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andReturn();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void findAllTest() throws Exception {
        mockMvc.perform(get("/api/session"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andReturn();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void createTest() throws Exception {
        SessionDto sessionDto = new SessionDto();

        sessionDto.setName("Session Test");
        sessionDto.setDescription("Descripion Test");
        sessionDto.setDate(Date.from(Instant.now()));
        sessionDto.setTeacher_id(1l);
        List<Long> users = new ArrayList<>();
        sessionDto.setUsers(users);

        String jsonRequest = objectMapper.writeValueAsString(sessionDto);

        // Perform post request
        mockMvc.perform(post("/api/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isOk());

        // Check creation of the session
        mockMvc.perform(get("/api/session/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Session Test")));
    }
}
