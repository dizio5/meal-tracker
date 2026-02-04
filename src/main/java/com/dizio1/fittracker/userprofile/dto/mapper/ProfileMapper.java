package com.dizio1.fittracker.userprofile.dto.mapper;

import com.dizio1.fittracker.user.entity.User;
import com.dizio1.fittracker.userprofile.dto.ProfileRequest;
import com.dizio1.fittracker.userprofile.dto.ProfileResponse;
import com.dizio1.fittracker.userprofile.entity.UserProfile;
import org.springframework.stereotype.Component;

@Component
public class ProfileMapper {

    public UserProfile toEntity(User user, ProfileRequest request) {
        UserProfile profile = new UserProfile();
        profile.setUser(user);
        profile.setAge(request.age());
        profile.setWeight(request.weight());
        profile.setHeight(request.height());
        return profile;
    }

    public ProfileResponse toResponse(String username, UserProfile profile) {
        return new ProfileResponse(username, profile.getAge(), profile.getWeight(), profile.getHeight());
    }

    public UserProfile updateProfile(UserProfile profile, ProfileRequest request) {
        if (request.age() != null) profile.setAge(request.age());
        if (request.height() != null) profile.setHeight(request.height());
        if (request.weight() != null) profile.setWeight(request.weight());

        return profile;
    }
}
