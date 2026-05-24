package com.DevConnect.service;

import com.DevConnect.dto.LoginRequest;
import com.DevConnect.dto.RegisterRequest;
import com.DevConnect.dto.AuthResponse;
import com.DevConnect.entity.User;
import com.DevConnect.repository.UserRepository;
import com.DevConnect.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder, 
                       AuthenticationManager authenticationManager,
                       JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public AuthResponse login(LoginRequest request) {
        try {
            // Authenticate user credentials
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            
            // Retrieve the authenticated user from database
            User user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            
            // Generate JWT token
            String token = jwtService.generateToken(user);
            
            // Return JWT token wrapped in response DTO
            return new AuthResponse(token);
            
        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid email or password", e);
        }
    }

    public String register(RegisterRequest request) {
        // Check if email already exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }
        
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
        return "User registered successfully!";
    }
}
