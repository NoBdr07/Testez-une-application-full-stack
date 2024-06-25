package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Test
    void findByIdTest() {
        // GIVEN
        User user = new User();
        user.setId(1L);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        //WHEN
        UserService userService = new UserService(userRepository);
        User foundUser = userService.findById(user.getId());

        // THEN
        assertEquals(user.getId(), foundUser.getId());
    }

    @Test
    void delete() {
        // GIVEN
        User user = new User();
        user.setId(1L);
        doNothing().when(userRepository).deleteById(anyLong());

        // WHEN
        UserService userService = new UserService(userRepository);
        userService.delete(user.getId());

        // THEN
        verify(userRepository, times(1)).deleteById(user.getId());
    }
}
