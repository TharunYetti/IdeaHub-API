package com.tharunyetti.IdeaHub.service;

import org.springframework.security.oauth2.core.user.OAuth2User;

import com.tharunyetti.IdeaHub.utility.AuthRequest;
import com.tharunyetti.IdeaHub.utility.AuthResponse;
import com.tharunyetti.IdeaHub.utility.UserDetails;

public interface AuthService {
    String registerUser(UserDetails userDetails);

    AuthResponse authenticate(AuthRequest authRequest);

    AuthResponse processGoogleUser(OAuth2User oauthUser);
}
