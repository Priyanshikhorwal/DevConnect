package com.DevConnect.service;

import com.DevConnect.dto.ProfileRequest;
import com.DevConnect.entity.Profile;
import com.DevConnect.repository.ProfileRepository;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public Profile createProfile(ProfileRequest request) {

        Profile profile = new Profile();

        profile.setBio(request.getBio());
        profile.setSkills(request.getSkills());
        profile.setLocation(request.getLocation());

        return profileRepository.save(profile);
    }
}