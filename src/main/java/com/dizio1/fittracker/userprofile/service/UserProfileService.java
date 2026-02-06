package com.dizio1.fittracker.userprofile.service;

import com.dizio1.fittracker.common.exception.UserNotFoundException;
import com.dizio1.fittracker.user.entity.User;
import com.dizio1.fittracker.user.repository.UserRepository;
import com.dizio1.fittracker.userprofile.dto.ProfileRequest;
import com.dizio1.fittracker.userprofile.dto.ProfileResponse;
import com.dizio1.fittracker.userprofile.dto.mapper.ProfileMapper;
import com.dizio1.fittracker.userprofile.entity.UserProfile;
import com.dizio1.fittracker.userprofile.exception.DuplicateProfileException;
import com.dizio1.fittracker.userprofile.repository.UserProfileRepository;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService {

    private final UserProfileRepository profileRepo;
    private final UserRepository userRepo;
    private final ProfileMapper mapper;

    public UserProfileService(UserRepository userRepo, UserProfileRepository profileRepo, ProfileMapper mapper) {
        this.profileRepo = profileRepo;
        this.userRepo = userRepo;
        this.mapper = mapper;
    }

    public ProfileResponse createUserProfile(String username, ProfileRequest request) {
        if (profileRepo.existsByUserUsername(username)) {
            throw new DuplicateProfileException(username);
        }

        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        UserProfile userProfile = mapper.toEntity(user, request);
        UserProfile saved = profileRepo.save(userProfile);

        return mapper.toResponse(username, saved);
    }

    public ProfileResponse updateUserProfile(String username, ProfileRequest request) {
        UserProfile userProfile = profileRepo.findByUserUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        UserProfile saved = profileRepo.save(mapper.updateProfile(userProfile, request));
        return mapper.toResponse(username,saved);
    }
}
