package com.openclassrooms.starterjwt.controllers.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.mapper.SessionMapper;
import com.openclassrooms.starterjwt.services.SessionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    /**
     * Test the findById request
     * A session with id 1 must exist in db.
     *
     * @throws Exception
     */
    @Test
    @WithMockUser(roles = "ADMIN")
    public void findByIdTest() throws Exception {
        mockMvc.perform(get("/api/session/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }

    /**
     * Test findAll request.
     * A session with id 1 must exist in db.
     *
     * @throws Exception
     */
    @Test
    @WithMockUser(roles = "ADMIN")
    public void findAllTest() throws Exception {
        mockMvc.perform(get("/api/session"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)));

    }

    /**
     * Create a session and then delete it.
     * It tests both method.
     * And can be re-run as we delete it.
     *
     * @throws Exception
     */
    @Test
    @WithMockUser(roles = "ADMIN")
    public void createAndDeleteTest() throws Exception {
        SessionDto sessionDto = new SessionDto();

        sessionDto.setName("Session Test");
        sessionDto.setDescription("Description Test");
        sessionDto.setDate(Date.from(Instant.now()));
        sessionDto.setTeacher_id(1l);
        List<Long> users = new ArrayList<>();
        sessionDto.setUsers(users);

        String jsonRequest = objectMapper.writeValueAsString(sessionDto);

        // Perform post request
        MvcResult postResult = mockMvc.perform(post("/api/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isOk())
                .andReturn();

        // Extraction of the id of the new session
        String responseContent = postResult.getResponse().getContentAsString();
        Long createdSessionId = JsonPath.parse(responseContent).read("$.id", Long.class);

        // Check creation of the session
        mockMvc.perform(get("/api/session/" + createdSessionId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Session Test")));

        // Clean up by deleting session 3
        mockMvc.perform(delete("/api/session/" + createdSessionId))
                .andExpect(status().isOk());

        // Check if session 3 is deleted
        mockMvc.perform(get("/api/session/" + createdSessionId))
                .andExpect(status().isNotFound());
    }

    /**
     * Test updating a session.
     * A session with id 5 must exist in db
     *
     * @throws Exception
     */
    @Test
    @WithMockUser(roles = "ADMIN")
    public void updateTest() throws Exception {

        // Create a sessionDto with the new info for session 3
        SessionDto sessionDto = new SessionDto();
        sessionDto.setName("Updated Name Test");
        sessionDto.setDescription("Updated Description Test");
        sessionDto.setDate(new Date());
        sessionDto.setTeacher_id(1l);

        mockMvc.perform(put("/api/session/5")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(new ObjectMapper().writeValueAsString(sessionDto)))
                  .andExpect(status().isOk());

        mockMvc.perform(get("/api/session/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Updated Name Test")))
                .andExpect(jsonPath("$.description", is("Updated Description Test")))
                .andExpect(jsonPath("$.teacher_id", is(1)));
    }

    /**
     * Test participate and noLongerParticipate requests.
     * Can be re-run as we do a noLongerParticipate
     * for the same user and session.
     *
     * A session with id 1 and
     * a user with id 4
     * must exist in db.
     *
     * @throws Exception
     */
    @Test
    @WithMockUser(roles = "ADMIN")
    public void participateTest() throws Exception {
        // Perform the post request
        mockMvc.perform(post("/api/session/1/participate/4"))
                .andExpect(status().isOk());

        // Check if user is in the users list of the session
        mockMvc.perform(get("/api/session/1"))
                .andExpect(jsonPath("$.users", hasItem(4)));

        // Cleanup: Remove user 4 from session 1
        mockMvc.perform(delete("/api/session/1/participate/4"))
                .andExpect(status().isOk());

    }




}
