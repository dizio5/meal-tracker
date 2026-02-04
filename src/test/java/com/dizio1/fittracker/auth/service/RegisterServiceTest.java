package com.dizio1.fittracker.auth.service;

import com.dizio1.fittracker.auth.dto.AuthResponse;
import com.dizio1.fittracker.auth.dto.RegisterRequest;
import com.dizio1.fittracker.auth.dto.mapper.AuthMapper;
import com.dizio1.fittracker.auth.exception.DuplicateMailException;
import com.dizio1.fittracker.auth.exception.DuplicateUsernameException;
import com.dizio1.fittracker.auth.exception.PasswordMissmatchException;
import com.dizio1.fittracker.security.service.JwtTokenService;
import com.dizio1.fittracker.user.entity.User;
import com.dizio1.fittracker.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RegisterServiceTest {

    @Mock private UserRepository userRepo;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private JwtTokenService jwtTokenService;

    @InjectMocks
    private RegisterService registerService;

    @BeforeEach
    public void setUp() {
        AuthMapper authMapper = new AuthMapper();
        registerService = new RegisterService(passwordEncoder, userRepo, jwtTokenService, authMapper);
    }

    @Test
    public void register_registerUserSuccessfully() {
        long expiresIn = 900;
        RegisterRequest request = new RegisterRequest(
                "John", "example@mail.com", "12345", "12345"
        );

        when(userRepo.existsByEmail(request.email())).thenReturn(false);
        when(userRepo.existsByUsername(request.username())).thenReturn(false);
        when(userRepo.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));
        when(passwordEncoder.encode(request.password())).thenReturn("encoded-password");
        when(jwtTokenService.generateToken(request.username(), expiresIn)).thenReturn("jwt-token");

        AuthResponse response = registerService.register(request);

        assertNotNull(response.token());
        verify(userRepo).save(any(User.class));
    }

    @Test
    public void register_whenPasswordMismatch_throwsMissmatchException() {
        RegisterRequest request = new RegisterRequest(
                "John", "example@mail.com", "12345", "5678"
        );

        assertThrows(PasswordMissmatchException.class, () -> registerService.register(request));

        verify(userRepo, never()).save(any(User.class));
    }

    @Test
    public void register_whenEmailIsDuplicate_throwsDuplicateMailException() {
        RegisterRequest request = new RegisterRequest(
                "John", "example@mail.com", "12345", "12345"
        );

        when(userRepo.existsByEmail(request.email())).thenReturn(true);

        assertThrows(DuplicateMailException.class, () -> registerService.register(request));

        verify(userRepo, never()).save(any(User.class));
    }

    @Test
    public void register_whenUsernameIsDuplicate_throwsDuplicateUsernameException() {
        RegisterRequest request = new RegisterRequest(
                "John", "example@mail.com", "12345", "56789"
        );

        when(userRepo.existsByUsername(request.username())).thenThrow(DuplicateUsernameException.class);

        assertThrows(DuplicateUsernameException.class, () -> registerService.register(request));

        verify(userRepo, never()).save(any(User.class));
    }
}