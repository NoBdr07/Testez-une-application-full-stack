package com.openclassrooms.starterjwt.controllers.unit;

import com.openclassrooms.starterjwt.controllers.AuthController;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.payload.request.LoginRequest;
import com.openclassrooms.starterjwt.payload.request.SignupRequest;
import com.openclassrooms.starterjwt.payload.response.JwtResponse;
import com.openclassrooms.starterjwt.payload.response.MessageResponse;
import com.openclassrooms.starterjwt.repository.UserRepository;
import com.openclassrooms.starterjwt.security.jwt.JwtUtils;
import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    private AuthenticationManager   authenticationManager;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    private AuthController authController;

    @BeforeEach
    public void setUp() {
        // Create an instance of AuthController
        authController = new AuthController(authenticationManager,
                passwordEncoder, jwtUtils, userRepository);
    }

    @Test
    public void authenticateUserTest() {
        // GIVEN
        // Mock user info
        Long id = 2L;
        String email = "test@test.com";
        String password = "test";
        String firstName = "test";
        String lastName = "test";
        boolean isAdmin = false;

        UserDetailsImpl userDetails = UserDetailsImpl.builder()
                .username(email).firstName(firstName).lastName(lastName)
                .id(id).admin(isAdmin).password(password).build();

        // Mock token
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null);

        // Mock security methods
        when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password)))
                .thenReturn(authentication);
        when(jwtUtils.generateJwtToken(authentication)).thenReturn("jwt");
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(User.builder().id(id).email(email)
                .password(password).firstName(firstName).lastName(lastName).admin(isAdmin).build()));

        // Create login request
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(email);
        loginRequest.setPassword(password);

        // WHEN
        ResponseEntity<?> response = authController.authenticateUser(loginRequest);
        JwtResponse responseBody = (JwtResponse) response.getBody();

        // THEN
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(email, responseBody.getUsername());
        assertEquals(firstName, responseBody.getFirstName());
        assertEquals(lastName, responseBody.getLastName());
        assertEquals(id, responseBody.getId());
        assertEquals(isAdmin, responseBody.getAdmin());
        assertEquals("Bearer", responseBody.getType());
        assertNotNull(responseBody.getToken());
    }

    @Test
    public void registerUserTest() {
        // GIVEN
        String email = "test@test.com";
        String firstName = "test";
        String lastName = "TEST";
        String password = "test123";

        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail(email);
        signupRequest.setFirstName(firstName);
        signupRequest.setLastName(lastName);
        signupRequest.setPassword(password);

        User user = new User(signupRequest.getEmail(),
                signupRequest.getLastName(),
                signupRequest.getFirstName(),
                signupRequest.getPassword(),
                false);

        when(userRepository.existsByEmail(email)).thenReturn(false);
        when(passwordEncoder.encode(signupRequest.getPassword())).thenReturn(password);
        when(userRepository.save(user)).thenReturn(user);

        // WHEN
        ResponseEntity<?> response = authController.registerUser(signupRequest);
        MessageResponse responseBody = (MessageResponse) response.getBody();

        // THEN
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User registered successfully!", responseBody.getMessage());
    }

    @Test
    public void registerUserFailTest() {
        // GIVEN
        String email = "test@test.com"; // email already taken
        String firstName = "test";
        String lastName = "TEST";
        String password = "test123";

        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail(email);
        signupRequest.setFirstName(firstName);
        signupRequest.setLastName(lastName);
        signupRequest.setPassword(password);

        when(userRepository.existsByEmail(email)).thenReturn(true);

        // WHEN
        ResponseEntity<?> response = authController.registerUser(signupRequest);
        MessageResponse responseBody = (MessageResponse) response.getBody();

        // THEN
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error: Email is already taken!", responseBody.getMessage());
    }


}
