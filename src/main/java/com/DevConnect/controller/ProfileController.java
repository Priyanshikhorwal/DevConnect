package com.DevConnect.controller;

import com.DevConnect.dto.ProfileRequest;
import com.DevConnect.entity.Profile;
import com.DevConnect.service.ProfileService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping
    public ResponseEntity<Profile> createProfile(@Valid @RequestBody ProfileRequest request, Authentication auth) {
        Profile profile = profileService.createProfile(request);
        return ResponseEntity.ok(profile);
    }

    @GetMapping("/me")
    public ResponseEntity<Profile> getMyProfile(Authentication auth) {
        String email = auth.getName();
        Profile profile = profileService.getProfileByEmail(email);
        return ResponseEntity.ok(profile);
    }

    @GetMapping("/{username}")
    public ResponseEntity<Profile> getProfile(@PathVariable String username) {
        Profile profile = profileService.getProfileByUsername(username);
        return ResponseEntity.ok(profile);
    }
}
