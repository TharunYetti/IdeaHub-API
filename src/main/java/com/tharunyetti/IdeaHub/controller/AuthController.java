package com.tharunyetti.IdeaHub.controller;

import com.tharunyetti.IdeaHub.service.AuthService;
import com.tharunyetti.IdeaHub.utility.AuthRequest;
import com.tharunyetti.IdeaHub.utility.AuthResponse;
import com.tharunyetti.IdeaHub.utility.UserDetails;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDetails userDetails) {
        return ResponseEntity.ok(authService.registerUser(userDetails));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        return ResponseEntity.ok(authService.authenticate(authRequest));
    }

    @GetMapping("/google/success")
    public ResponseEntity<AuthResponse> googleLoginSuccess(@AuthenticationPrincipal OAuth2User principal) {
        return ResponseEntity.ok(authService.processGoogleUser(principal));
    }

    @GetMapping("/google/failure")
    public ResponseEntity<String> googleLoginFailure() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Google authentication failed");
    }
}
