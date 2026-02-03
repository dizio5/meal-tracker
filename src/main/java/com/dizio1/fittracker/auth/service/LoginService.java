package com.dizio1.fittracker.auth.service;

import com.dizio1.fittracker.auth.dto.AuthResponse;
import com.dizio1.fittracker.auth.dto.LoginRequest;
import com.dizio1.fittracker.auth.dto.mapper.AuthMapper;
import com.dizio1.fittracker.security.service.JwtTokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;


@Service
public class LoginService {
    private final AuthMapper authMapper;
    private final AuthenticationManager authManager;
    private final JwtTokenService jwtTokenService;

    public LoginService(AuthMapper authMapper,
                        AuthenticationManager authManager,
                        JwtTokenService jwtTokenService) {
        this.authMapper = authMapper;
        this.authManager = authManager;
        this.jwtTokenService = jwtTokenService;
    }

    public AuthResponse login(LoginRequest request) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password())
        );

        String token = jwtTokenService.generateToken(auth.getName(), 15 * 60);
        return authMapper.toResponse(token);
    }
}
