package com.openclassrooms.starterjwt.security.jwt;

import com.openclassrooms.starterjwt.security.services.UserDetailsServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import javax.servlet.ServletException;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AuthTokenFilterTest {

    @MockBean
    private JwtUtils jwtUtils;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthTokenFilter authTokenFilter;

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockFilterChain filterChain;

    @BeforeEach
    void beforeEach() {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        filterChain = new MockFilterChain();
    }

    @Test
    public void doFilterInternalTest() throws ServletException, IOException {
        // GIVEN
        String validJwt = "validToken";
        String username = "test@mail.com";
        UserDetails userDetails = mock(UserDetails.class);

        request.addHeader("Authorization", "Bearer " + validJwt);

        when(jwtUtils.validateJwtToken(validJwt)).thenReturn(true);
        when(jwtUtils.getUserNameFromJwtToken(validJwt)).thenReturn(username);
        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);

        // WHEN
        authTokenFilter.doFilterInternal(request, response, filterChain);

        // THEN
        verify(jwtUtils, times(1)).validateJwtToken(validJwt);
        verify(jwtUtils, times(1)).getUserNameFromJwtToken(validJwt);
        verify(userDetailsService, times(1)).loadUserByUsername(username);
        assertEquals(userDetailsService.loadUserByUsername(username), userDetails);
    }

    @Test
    public void doFilterInternalFailTest() throws ServletException, IOException {
        // GIVEN
        String invalidJwt = "invalidToken";
        request.addHeader("Authorization", "Bearer " + invalidJwt);
        when(jwtUtils.validateJwtToken(invalidJwt)).thenReturn(false);

        // WHEN
        authTokenFilter.doFilterInternal(request, response, filterChain);

        // THEN
        verify(jwtUtils, times(1)).validateJwtToken(invalidJwt);
        verify(jwtUtils, never()).getUserNameFromJwtToken(invalidJwt);
        verify(userDetailsService, never()).loadUserByUsername(anyString());
    }

    // Vérify that the filter does not call the JWTUtils and UserDetailsService when the token is not provided
    @Test
    public void doFilterInternalUnauthorizedTest() throws ServletException, IOException {
        // WHEN
        authTokenFilter.doFilterInternal(request, response, filterChain);

        // THEN
        verify(jwtUtils, never()).validateJwtToken(anyString());
        verify(jwtUtils, never()).getUserNameFromJwtToken(anyString());
        verify(userDetailsService, never()).loadUserByUsername(anyString());
    }
}
