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

        // 3. Check if profile already exists
        Profile profile = profileRepository.findByUser(user)
                .orElse(new Profile());

        // 4. Update or create profile
        profile.setBio(request.getBio());
        profile.setSkills(request.getSkills());
        profile.setLocation(request.getLocation());
        profile.setUser(user);

        return profileRepository.save(profile);
    }

    public Profile getProfileByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return profileRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found"));
    }

    public Profile getProfileByUsername(String username) {
        return profileRepository.findByUserUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found"));
    }
}
