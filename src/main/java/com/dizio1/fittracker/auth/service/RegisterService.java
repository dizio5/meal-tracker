package com.dizio1.fittracker.auth.service;

import com.dizio1.fittracker.auth.dto.mapper.AuthMapper;
import com.dizio1.fittracker.auth.exception.DuplicateMailException;
import com.dizio1.fittracker.auth.exception.DuplicateUsernameException;
import com.dizio1.fittracker.auth.dto.AuthResponse;
import com.dizio1.fittracker.auth.dto.RegisterRequest;
import com.dizio1.fittracker.auth.exception.PasswordMissmatchException;
import com.dizio1.fittracker.security.service.JwtTokenService;
import com.dizio1.fittracker.user.entity.UserRole;
import com.dizio1.fittracker.user.repository.UserRepository;
import com.dizio1.fittracker.user.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthMapper authMapper;
    private final JwtTokenService jwtTokenService;

    public RegisterService(
            PasswordEncoder passwordEncoder,
            UserRepository userRepository,
            JwtTokenService jwtTokenService,
            AuthMapper authMapper) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.jwtTokenService = jwtTokenService;
        this.authMapper = authMapper;
    }

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.username())) throw new DuplicateUsernameException(request.username());
        if (userRepository.existsByEmail(request.email())) throw new DuplicateMailException(request.email());

        if(!request.password().equals(request.confirmPassword())) {
            throw new PasswordMissmatchException();
        }

        User user = new User();
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setEmail(request.email());
        user.setRole(UserRole.USER);

        User saved = userRepository.save(user);

        String token = jwtTokenService.generateToken(saved.getUsername(), 15 * 60);
        return authMapper.toResponse(token);
    }
}
