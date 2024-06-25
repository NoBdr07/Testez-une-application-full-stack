package com.openclassrooms.starterjwt.controllers.unit;

import com.openclassrooms.starterjwt.controllers.SessionController;
import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.mapper.SessionMapper;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.services.SessionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class SessionControllerTest {

    @Mock
    private SessionMapper sessionMapper;

    @Mock
    private SessionService sessionService;

    private SessionController sessionController;

    @BeforeEach
    public void setUp() {
        sessionController = new SessionController(sessionService, sessionMapper);
    }

    @Test
    public void findByIdTest() {
        // GIVEN
        Long id = 1L;

        Teacher teacher = new Teacher();

        Session session = new Session();
        session.setId(id);
        session.setName("Yoga");
        session.setTeacher(teacher);
        session.setUsers(new ArrayList<>());
        session.setDate(new Date());

        SessionDto sessionDto = new SessionDto();
        sessionDto.setId(id);
        sessionDto.setName("Yoga");
        sessionDto.setTeacher_id(1L);
        sessionDto.setDate(new Date());

        when(sessionService.getById(id)).thenReturn(session);
        when(sessionMapper.toDto(session)).thenReturn(sessionDto);

        // WHEN
        ResponseEntity<?> response = sessionController.findById("1");

        // THEN
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sessionDto, response.getBody());
    }

    @Test
    public void findByIdFailTest() {
        // GIVEN
        when(sessionService.getById(1L)).thenReturn(null);

        // WHEN
        ResponseEntity<?> response = sessionController.findById("1");

        // THEN
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void updateTest() {
        // GIVEN
        Long id = 1L;
        Date date = new Date();

        Teacher teacher = new Teacher();

        Session session = new Session();
        session.setId(id);
        session.setName("Yoga");
        session.setTeacher(teacher);
        session.setUsers(new ArrayList<>());
        session.setDate(date);
        session.setDescription("Test description");

        SessionDto sessionDto = new SessionDto();
        sessionDto.setId(id);
        sessionDto.setName("Yoga");
        sessionDto.setTeacher_id(1L);
        sessionDto.setDate(date);
        sessionDto.setDescription("Test description");

        when(sessionService.update(id, session)).thenReturn(session);
        when(sessionMapper.toEntity(sessionDto)).thenReturn(session);
        when(sessionMapper.toDto(session)).thenReturn(sessionDto);

        // WHEN
        ResponseEntity<?> response = sessionController.update("1", sessionDto);

        // THEN
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sessionDto, response.getBody());

    }

    @Test
    public void updateFailTest() {
        // GIVEN
        String invalidId = "invalid_id"; // invalid ID that trigger NumberFormatException

        SessionDto sessionDto = new SessionDto();
        sessionDto.setId(1L);
        sessionDto.setName("Yoga");
        sessionDto.setTeacher_id(1L);
        sessionDto.setDate(new Date());
        sessionDto.setDescription("Test description");

        // WHEN
        ResponseEntity<?> response = sessionController.update(invalidId, sessionDto);

        // THEN
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void saveFailTest() {
        // GIVEN
        String invalidId = "invalid_id"; // invalid ID that trigger NumberFormatException

        SessionDto sessionDto = new SessionDto();
        sessionDto.setId(1L);
        sessionDto.setName("Yoga");
        sessionDto.setTeacher_id(1L);
        sessionDto.setDate(new Date());
        sessionDto.setDescription("Test description");

        // WHEN
        ResponseEntity<?> response = sessionController.save(invalidId);

        // THEN
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void participateTest() {
        // GIVEN
        String sessionId = "1";
        String userId = "2";

        // WHEN
        ResponseEntity<?> response =  sessionController.participate(sessionId, userId);

        // THEN
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
