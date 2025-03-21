package com.example.expensetracker.Controller;


import com.example.expensetracker.Entity.User;
import com.example.expensetracker.Exception.InvalidCredentialsException;
import com.example.expensetracker.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthController authController;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("testuser");
        user.setPassword("password123");
    }

    @Test
    void testRegister_Success() {
        // Mock password encoding
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");

        // Mock repository save method
        when(userRepository.save(user)).thenReturn(user);

        // Call the register method
        String response = authController.register(user);

        // Assertions
        assertEquals("User registered successfully!", response);
        verify(passwordEncoder).encode("password123"); // Ensure password is encoded
        verify(userRepository).save(user); // Ensure user is saved
    }

//    @Test
//    void testLogin_Success() {
//        // Mock authentication success
//        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
//                .thenReturn(mock(UsernamePasswordAuthenticationToken.class));
//
//        // Call the login method
//        //String response = authController.login(user);
//
//        // Assertions
//
//        assertEquals("Login successful!", response);
//        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
//    }

}

