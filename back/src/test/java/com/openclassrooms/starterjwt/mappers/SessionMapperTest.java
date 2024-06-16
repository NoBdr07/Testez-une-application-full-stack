package com.openclassrooms.starterjwt.mappers;

import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.dto.TeacherDto;
import com.openclassrooms.starterjwt.mapper.SessionMapper;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.services.TeacherService;
import com.openclassrooms.starterjwt.services.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class SessionMapperTest {

    @Autowired
    private SessionMapper sessionMapper;

    @Mock
    private TeacherService teacherService;

    @Mock
    private UserService userService;

    @Test
    public void SessionDtoToEntityTest() {
        // GIVEN
        SessionDto sessionDto = new SessionDto();

        sessionDto.setId(1L);
        sessionDto.setName("Yoga");
        sessionDto.setTeacher_id(1L);

        List<Long> users = new ArrayList<>();
        users.add(1L);
        users.add(2L);
        sessionDto.setUsers(users);

        Teacher teacher = new Teacher();
        teacher.setId(1L);
        User user1 = new User();
        user1.setId(1L);
        User user2 = new User();
        user2.setId(2L);

        when(teacherService.findById(1L)).thenReturn(teacher);
        when(userService.findById(1L)).thenReturn(user1);
        when(userService.findById(2L)).thenReturn(user2);

        // WHEN
        Session session = sessionMapper.toEntity(sessionDto);

        // THEN
        assertEquals(sessionDto.getId(), session.getId());
        assertEquals(sessionDto.getName(), session.getName());
        assertEquals(sessionDto.getTeacher_id(), session.getTeacher().getId());
        assertEquals(sessionDto.getUsers().size(), session.getUsers().size());
    }

    @Test
    public void SessionEntityToDtoTest() {
        // GIVEN
        Session session = new Session();

        session.setId(1L);
        session.setName("Yoga");

        Teacher teacher = new Teacher();
        teacher.setId(1L);
        session.setTeacher(teacher);

        User user1 = new User();
        user1.setId(1L);
        User user2 = new User();
        user2.setId(2L);
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        session.setUsers(users);

        // WHEN
        SessionDto sessionDto = sessionMapper.toDto(session);

        // THEN
        assertEquals(session.getId(), sessionDto.getId());
        assertEquals(session.getName(), sessionDto.getName());
        assertEquals(session.getTeacher().getId(), sessionDto.getTeacher_id());
        assertEquals(session.getUsers().size(), sessionDto.getUsers().size());
    }

    @Test
    public void SessionListDtoToEntityTest() {
        // GIVEN
        SessionDto sessionDto = new SessionDto();

        sessionDto.setId(1L);
        sessionDto.setName("Yoga");
        sessionDto.setTeacher_id(1L);

        List<Long> users = new ArrayList<>();
        users.add(1L);
        users.add(2L);
        sessionDto.setUsers(users);

        Teacher teacher = new Teacher();
        teacher.setId(1L);
        User user1 = new User();
        user1.setId(1L);
        User user2 = new User();


        List<SessionDto> sessionDtos = new ArrayList<>();
        sessionDtos.add(sessionDto);

        when(teacherService.findById(1L)).thenReturn(teacher);
        when(userService.findById(1L)).thenReturn(user1);
        when(userService.findById(2L)).thenReturn(user2);

        // WHEN
        List<Session> sessions = sessionMapper.toEntity(sessionDtos);

        // THEN
        assertEquals(sessionDtos.get(0).getId(), sessions.get(0).getId());
        assertEquals(sessionDtos.get(0).getName(), sessions.get(0).getName());
        assertEquals(sessionDtos.get(0).getTeacher_id(), sessions.get(0).getTeacher().getId());
        assertEquals(sessionDtos.get(0).getUsers().size(), sessions.get(0).getUsers().size());
    }

    @Test
    public void SessionListEntityToDtoTest() {
        // GIVEN
        Session session = new Session();

        session.setId(1L);
        session.setName("Yoga");

        Teacher teacher = new Teacher();
        teacher.setId(1L);
        session.setTeacher(teacher);

        User user1 = new User();
        user1.setId(1L);
        User user2 = new User();
        user2.setId(2L);
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        session.setUsers(users);

        List<Session> sessions = new ArrayList<>();
        sessions.add(session);

        // WHEN
        List<SessionDto> sessionDtos = sessionMapper.toDto(sessions);

        // THEN
        assertEquals(sessions.get(0).getId(), sessionDtos.get(0).getId());
        assertEquals(sessions.get(0).getName(), sessionDtos.get(0).getName());
        assertEquals(sessions.get(0).getTeacher().getId(), sessionDtos.get(0).getTeacher_id());
        assertEquals(sessions.get(0).getUsers().size(), sessionDtos.get(0).getUsers().size());
    }

    @Test
    public void SessionDtoToEntityNullTest() {
        // GIVEN
        SessionDto sessionDto = new SessionDto();
        sessionDto = null ;

        // WHEN
        Session session = sessionMapper.toEntity(sessionDto);

        // THEN
        assertEquals(session, null);
    }

    @Test
    public void SessionEntityToDtoNullTest() {
        // GIVEN
        Session session = new Session();
        session = null ;

        // WHEN
        SessionDto sessionDto = sessionMapper.toDto(session);

        // THEN
        assertEquals(sessionDto, null);
    }

    @Test
    public void TeacherListDtoToEntityNullTest() {
        // GIVEN
        List<SessionDto> sessionDtos = new ArrayList<>();
        sessionDtos = null ;

        // WHEN
        List<Session> sessions = sessionMapper.toEntity(sessionDtos);

        // THEN
        assertEquals(sessions, null);
    }

    @Test
    public void TeacherListEntityToDtoNullTest() {
        // GIVEN
        List<Session> sessions = new ArrayList<>();
        sessions = null ;

        // WHEN
        List<SessionDto> sessionDtos = sessionMapper.toDto(sessions);

        // THEN
        assertEquals(sessionDtos, null);
    }
}
