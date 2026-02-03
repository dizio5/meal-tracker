package com.dizio1.fittracker.userprofile.repository;

import com.dizio1.fittracker.user.entity.User;
import com.dizio1.fittracker.userprofile.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    boolean existsByUserUsername(String username);
    Optional<UserProfile> findByUserUsername(String username);
}
