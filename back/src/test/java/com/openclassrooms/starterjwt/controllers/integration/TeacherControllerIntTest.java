package com.openclassrooms.starterjwt.controllers.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@AutoConfigureMockMvc
public class TeacherControllerIntTest {
    @Autowired
    private MockMvc mockMvc;

    // A teacher with id 1 must exist in db.
    @Test
    @WithMockUser(roles = "ADMIN")
    public void findByIdTest() throws Exception {
        mockMvc.perform(get("/api/teacher/1", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }

     // A teacher with id 1 must exist in db.
    @Test
    @WithMockUser(roles = "ADMIN")
    public void findAllTest() throws Exception {
        mockMvc.perform(get("/api/teacher"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)));
    }
}
