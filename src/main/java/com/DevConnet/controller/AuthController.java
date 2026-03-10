package com.DevConnet.controller;
import com.DevConnet.service.AuthService;
import com.DevConnet.dto.RegisterRequest;
import com.DevConnet.service.AuthService;
import com.DevConnet.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

}