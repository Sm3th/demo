package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser() {
        User user = new User("testUser", "password123");

        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User registeredUser = userService.registerUser("testUser", "password123");

        assertNotNull(registeredUser);
        assertEquals("testUser", registeredUser.getUsername());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testFindByUsername() {
        User user = new User("testUser", "password123");

        when(userRepository.findByUsername("testUser")).thenReturn(user);

        User foundUser = userService.findByUsername("testUser");

        assertNotNull(foundUser);
        assertEquals("testUser", foundUser.getUsername());
        verify(userRepository, times(1)).findByUsername("testUser");
    }
}
