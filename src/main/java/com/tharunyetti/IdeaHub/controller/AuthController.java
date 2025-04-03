package com.tharunyetti.IdeaHub.controller;

import com.tharunyetti.IdeaHub.service.AuthService;
import com.tharunyetti.IdeaHub.utility.AuthRequest;
import com.tharunyetti.IdeaHub.utility.UserDetails;

import jakarta.servlet.http.Cookie;
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
    public ResponseEntity<String> login(@RequestBody AuthRequest authRequest) {
        String token = authService.authenticate(authRequest);
        System.out.println(token);
         // Create a secure HttpOnly cookie
        Cookie cookie = new Cookie("jwt", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // Set to true in production (HTTPS required)
        cookie.setPath("/"); // Accessible across all endpoints
        cookie.setMaxAge(60 * 60 * 12); // 1 hour expiration
        return ResponseEntity.ok(token);
    }

    @GetMapping("/google/success")
    public ResponseEntity<String> googleLoginSuccess(@AuthenticationPrincipal OAuth2User principal) {
        
        String token = authService.processGoogleUser(principal);
         // Create a secure HttpOnly cookie
        Cookie cookie = new Cookie("jwt", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // Set to true in production (HTTPS required)
        cookie.setPath("/"); // Accessible across all endpoints
        cookie.setMaxAge(60 * 60 * 12); // 1 hour expiration
        
        return ResponseEntity.ok(authService.processGoogleUser(principal));
    }

    @GetMapping("/google/failure")
    public ResponseEntity<String> googleLoginFailure() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Google authentication failed");
    }
}
