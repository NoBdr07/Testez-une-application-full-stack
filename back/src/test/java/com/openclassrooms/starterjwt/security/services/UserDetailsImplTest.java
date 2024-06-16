package com.openclassrooms.starterjwt.security.services;

import com.openclassrooms.starterjwt.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserDetailsImplTest {

    private UserDetailsImpl userDetails;

    @Test
    public void equalsTest() {
        // GIVEN
        UserDetailsImpl userDetailsImpl = UserDetailsImpl.builder().id(1L).build();
        UserDetailsImpl sameUserDetailsImpl = UserDetailsImpl.builder().id(1L).build();
        UserDetailsImpl differentUserDetailsImpl = UserDetailsImpl.builder().id(2L).build();
        UserDetailsImpl nullUserDetailsImpl = null ;


        // WHEN & THEN
        assertTrue(userDetailsImpl.equals(sameUserDetailsImpl));
        assertFalse(userDetailsImpl.equals(differentUserDetailsImpl));
        assertFalse(userDetailsImpl.equals(nullUserDetailsImpl));
    }

}
