package com.DevConnect.service;

import com.DevConnect.dto.LoginRequest;
import com.DevConnect.dto.RegisterRequest;
import com.DevConnect.entity.User;
import com.DevConnect.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public String login(LoginRequest request) {
        userRepository.findByEmail(request.getEmail()).ifPresent(user -> {})
    }

    public String register(RegisterRequest request){
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
        return "your Registered successfully!";
    }


}
