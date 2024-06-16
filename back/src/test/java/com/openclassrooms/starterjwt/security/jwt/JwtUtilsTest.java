package com.openclassrooms.starterjwt.security.jwt;

import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class JwtUtilsTest {

    @Autowired
    private JwtUtils jwtUtils;

    @Mock
    private Authentication authentication;

    @Mock
    private UserDetailsImpl userDetails;


    @Value("${oc.app.jwtSecret}")
    private String jwtSecret;

    @Value("${oc.app.jwtExpirationMs}")
    private int jwtExpirationMs;


    @Test
    public void generateJwtTokenTest() {
        // GIVEN
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("testUser");

        // WHEN
        String token = jwtUtils.generateJwtToken(authentication);

        // THEN
        assertNotNull(token);
    }

    @Test
    public void getUserNameFromJwtTokenTest() {
        // GIVEN
        String username = "testUser";
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn(username);

        // WHEN
        String token = jwtUtils.generateJwtToken(authentication);
        String usernameReturned = jwtUtils.getUserNameFromJwtToken(token);

        // THEN
        assertNotNull(token);
        assertEquals(username, usernameReturned);
    }

    @Test
    public void validateJwtTokenTest() {
        // GIVEN
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("testUser");

        // WHEN
        String token = jwtUtils.generateJwtToken(authentication);
        boolean validated = jwtUtils.validateJwtToken(token);

        // THEN
        assertNotNull(token);
        assertTrue(validated);
    }

}
