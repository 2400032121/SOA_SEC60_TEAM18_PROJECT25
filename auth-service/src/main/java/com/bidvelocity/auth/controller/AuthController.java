package com.bidvelocity.auth.controller;

import com.bidvelocity.auth.dto.AuthRequest;
import com.bidvelocity.auth.dto.AuthResponse;
import com.bidvelocity.auth.entity.User;
import com.bidvelocity.auth.security.JwtService;
import com.bidvelocity.auth.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final JwtService jwtService;

    public AuthController(AuthService authService, JwtService jwtService) {
        this.authService = authService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest request) {
        try {
            User user = authService.register(request.getUsername(), request.getPassword());
            return ResponseEntity.ok(Map.of(
                    "id", user.getId(),
                    "username", user.getUsername(),
                    "message", "Registration successful"
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            User user = authService.authenticate(request.getUsername(), request.getPassword());
            return ResponseEntity.ok(new AuthResponse(jwtService.generateToken(user.getUsername())));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(401).body(Map.of("message", e.getMessage()));
        }
    }
}
