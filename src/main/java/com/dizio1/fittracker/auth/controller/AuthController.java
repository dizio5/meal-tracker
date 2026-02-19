package com.dizio1.fittracker.auth.controller;

import com.dizio1.fittracker.auth.dto.AuthResponse;
import com.dizio1.fittracker.auth.dto.LoginRequest;
import com.dizio1.fittracker.auth.dto.RegisterRequest;
import com.dizio1.fittracker.auth.service.LoginService;
import com.dizio1.fittracker.auth.service.RegisterService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final RegisterService registerService;
    private final LoginService loginService;

    public AuthController(RegisterService registerService, LoginService loginService) {
        this.registerService = registerService;
        this.loginService = loginService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthResponse register(@RequestBody RegisterRequest request) {
        return registerService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return loginService.login(request);
    }
}
