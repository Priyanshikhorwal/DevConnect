package com.DevConnect.service;

import com.DevConnect.dto.ProfileRequest;
import com.DevConnect.entity.Profile;
import com.DevConnect.entity.User;
import com.DevConnect.exception.ResourceNotFoundException;
import com.DevConnect.repository.ProfileRepository;
import com.DevConnect.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    public ProfileService(ProfileRepository profileRepository, UserRepository userRepository) {
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
    }

    public Profile createProfile(ProfileRequest request) {

        // 1. Extract the authenticated user's email
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        // 2. Fetch the User from the database
        User user = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // 3. Create and associate the Profile
        Profile profile = new Profile();
        profile.setBio(request.getBio());
        profile.setSkills(request.getSkills());
        profile.setLocation(request.getLocation());
        profile.setUser(user);

        return profileRepository.save(profile);
    }
}