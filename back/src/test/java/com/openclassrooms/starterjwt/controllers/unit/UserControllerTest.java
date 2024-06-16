package com.openclassrooms.starterjwt.controllers.unit;

import com.openclassrooms.starterjwt.controllers.SessionController;
import com.openclassrooms.starterjwt.controllers.UserController;
import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.mapper.SessionMapper;
import com.openclassrooms.starterjwt.mapper.UserMapper;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.services.SessionService;
import com.openclassrooms.starterjwt.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserMapper userMapper;

    @Mock
    private UserService userService;

    private UserController userController;

    @BeforeEach
    public void setUp() {
        userController = new UserController(userService, userMapper);
    }

    @Test
    public void findByIdTest() {
        // GIVEN
        User user = new User() ;

        when(userService.findById(1L)).thenReturn(new User());
        when(userMapper.toDto(user)).thenReturn(new UserDto());

        // WHEN
        ResponseEntity<?> responseEntity = userController.findById(String.valueOf(1L));

        // THEN
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void findByIdNotFoundTest() {
        // GIVEN
        when(userService.findById(0L)).thenReturn(null);

        // WHEN
        ResponseEntity<?> responseEntity = userController.findById(String.valueOf(0L));

        // THEN
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }


    @Test
    public void findByIdBadRequestTest() {
        // WHEN
        ResponseEntity<?> responseEntity = userController.findById("wrongId");

        // THEN
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void saveTest() {
        // GIVEN
        long id = 1L;
        User user = new User();
        user.setEmail("test@example.com");

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        when(userService.findById(id)).thenReturn(user);

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getEmail(),
                "password", authorities);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,
                null);

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        // WHEN
        ResponseEntity<?> responseEntity = userController.save(String.valueOf(id));

        // THEN
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(userService, times(1)).delete(id);
    }

}
