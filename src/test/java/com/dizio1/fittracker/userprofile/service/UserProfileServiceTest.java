package com.dizio1.fittracker.userprofile.service;

import com.dizio1.fittracker.user.entity.User;
import com.dizio1.fittracker.user.repository.UserRepository;
import com.dizio1.fittracker.userprofile.dto.ProfileRequest;
import com.dizio1.fittracker.userprofile.dto.ProfileResponse;
import com.dizio1.fittracker.userprofile.dto.mapper.ProfileMapper;
import com.dizio1.fittracker.userprofile.entity.UserProfile;
import com.dizio1.fittracker.userprofile.exception.DuplicateProfileException;
import com.dizio1.fittracker.userprofile.repository.UserProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserProfileServiceTest {

    @Mock
    private UserProfileRepository profileRepo;
    @Mock
    private UserRepository userRepo;

    @InjectMocks
    private UserProfileService profileService;

    @BeforeEach
    void setUp() {
        ProfileMapper mapper = new ProfileMapper();
        profileService = new UserProfileService(userRepo, profileRepo, mapper);
    }

    @Test
    public void createUserProfile_createProfileSuccessfully() {
        ProfileRequest request = new ProfileRequest(22, 60.0, 173.0);

        User user = new User();
        user.setUsername("John Doe");
        user.setPassword("1234");

        when(profileRepo.existsByUserUsername(user.getUsername())).thenReturn(false);
        when(userRepo.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(profileRepo.save(any(UserProfile.class))).thenAnswer(inv -> inv.getArgument(0));

        ProfileResponse response = profileService.createUserProfile(user.getUsername(), request);

        assertEquals("John Doe", response.username());
        assertEquals(request.age(), response.age());
        assertEquals(request.weight(), response.weight());
        assertEquals(request.height(), response.height());

        verify(profileRepo).save(any(UserProfile.class));
    }

    @Test
    public void createUserProfile_whenUserHasProfile_throwsDuplicateProfileException() {
        ProfileRequest request = new ProfileRequest(22, 60.0, 173.0);
        String username = "Facundo";

        when(profileRepo.existsByUserUsername(username)).thenReturn(true);

        assertThrows(DuplicateProfileException.class, () -> profileService.createUserProfile(username, request));

        verify(profileRepo, never()).save(any(UserProfile.class));
    }
}
