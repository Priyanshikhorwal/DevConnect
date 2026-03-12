package com.DevConnect.controller;
import com.DevConnect.service.AuthService;
import com.DevConnect.dto.RegisterRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public String register( @Valid @RequestBody RegisterRequest request) {
        return authService.register(request);
    }

}