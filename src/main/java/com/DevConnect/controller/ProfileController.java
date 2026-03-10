package com.DevConnect.controller;

import com.DevConnect.dto.ProfileRequest;
import com.DevConnect.entity.Profile;
import com.DevConnect.service.ProfileService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService){
        this.profileService = profileService;
    }

    @PostMapping
    public Profile createProfile(@RequestBody ProfileRequest request){
        return profileService.createProfile(request);
    }
}
