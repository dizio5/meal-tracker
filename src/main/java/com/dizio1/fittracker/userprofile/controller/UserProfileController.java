package com.dizio1.fittracker.userprofile.controller;

import com.dizio1.fittracker.userprofile.dto.ProfileRequest;
import com.dizio1.fittracker.userprofile.dto.ProfileResponse;
import com.dizio1.fittracker.userprofile.service.UserProfileService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/profile")
public class UserProfileController {

    private final UserProfileService profileService;

    public UserProfileController(UserProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping
    public ProfileResponse createUserProfile(
            @AuthenticationPrincipal Jwt jwt,
            @RequestBody ProfileRequest request) {
        return profileService.createUserProfile(jwt.getSubject(), request);
    }

    @PatchMapping
    public ProfileResponse updateUserProfile(
            @AuthenticationPrincipal Jwt jwt,
            @RequestBody ProfileRequest request) {
        return profileService.updateUserProfile(jwt.getSubject(), request);
    }
}
