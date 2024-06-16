package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.SessionRepository;
import com.openclassrooms.starterjwt.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SessionServiceTest {

    @Mock
    SessionRepository sessionRepository;

    @Mock
    UserRepository userRepository;


    @Test
    void createTest() {
        // GIVEN
        Session session = new Session();
        when(sessionRepository.save(any())).thenReturn(session);

        // WHEN
        SessionService sessionService = new SessionService(sessionRepository, userRepository);
        Session sessionFound = sessionService.create(session);

        // THEN
        assertEquals(session, sessionFound);
    }

    @Test
    void deleteTest() {
        // GIVEN
        Session session = new Session();
        session.setId(1L);
        doNothing().when(sessionRepository).deleteById(any());

        // WHEN
        SessionService sessionService = new SessionService(sessionRepository, userRepository);
        sessionService.delete(session.getId());

        // THEN
        verify(sessionRepository, times(1)).deleteById(session.getId());

    }

    @Test
    void findAllTest() {
        // GIVEN
        List<Session> sessions = new ArrayList<>();
        when(sessionRepository.findAll()).thenReturn(sessions);

        // WHEN
        SessionService sessionService = new SessionService(sessionRepository, userRepository);
        List<Session> sessionsFound = sessionService.findAll();

        // THEN
        assertEquals(sessions, sessionsFound);
    }

    @Test
    void getByIdTest() {
        // GIVEN
        Session session = new Session();
        session.setId(1L);
        when(sessionRepository.findById(anyLong())).thenReturn(Optional.of(session));

        // WHEN
        SessionService sessionService = new SessionService(sessionRepository, userRepository);
        Session sessionFound = sessionService.getById(1L);

        // THEN
        assertEquals(session, sessionFound);
    }

    @Test
    void updateTest() {
        // GIVEN
        Session session = new Session();
        session.setId(1L);
        when(sessionRepository.save(any())).thenReturn(session);

        // WHEN
        SessionService sessionService = new SessionService(sessionRepository, userRepository);
        Session sessionFound = sessionService.update(2L, session);

        // THEN
        assertEquals(session, sessionFound);
    }

    @Test
    void participateTest() {
        // GIVEN
        Session session = new Session();
        session.setId(1L);
        session.setUsers(new ArrayList<>());
        User user = new User();
        user.setId(1L);
        when(sessionRepository.findById(anyLong())).thenReturn(Optional.of(session));
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(sessionRepository.save(any())).thenReturn(session);

        // WHEN
        SessionService sessionService = new SessionService(sessionRepository, userRepository);
        sessionService.participate(session.getId(), user.getId());

        // THEN
        verify(sessionRepository, times(1)).findById(session.getId());
        verify(userRepository, times(1)).findById(user.getId());
        verify(sessionRepository, times(1)).save(session);
    }

    @Test
    void noLongerParticipateTest() {
        // GIVEN
        Session session = new Session();
        session.setId(1L);
        List<User> users = new ArrayList<>();
        User user = new User();
        user.setId(1L);
        users.add(user);
        session.setUsers(users);
        when(sessionRepository.findById(anyLong())).thenReturn(Optional.of(session));
        when(sessionRepository.save(any())).thenReturn(session);

        // WHEN
        SessionService sessionService = new SessionService(sessionRepository, userRepository);
        sessionService.noLongerParticipate(session.getId(), user.getId());

        // THEN
        verify(sessionRepository, times(1)).findById(session.getId());
        verify(sessionRepository, times(1)).save(session);
    }
}
