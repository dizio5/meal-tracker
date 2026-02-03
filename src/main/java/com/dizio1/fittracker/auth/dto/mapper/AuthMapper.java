package com.dizio1.fittracker.auth.dto.mapper;

import com.dizio1.fittracker.auth.dto.AuthResponse;
import com.dizio1.fittracker.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {

    public AuthResponse toResponse(String token) {
        return new AuthResponse(
                token, "Bearer", 900
        );
    }
}
