package com.tharunyetti.IdeaHub.service;

import com.tharunyetti.IdeaHub.entity.User;
import com.tharunyetti.IdeaHub.enums.Role;
import com.tharunyetti.IdeaHub.repository.UserRepository;
import com.tharunyetti.IdeaHub.utility.AuthRequest;
import com.tharunyetti.IdeaHub.utility.AuthResponse;
import com.tharunyetti.IdeaHub.utility.JwtUtil;
import com.tharunyetti.IdeaHub.utility.UserDetails;
import com.tharunyetti.IdeaHub.utility.UserOAuthDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    public AuthServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String registerUser(UserDetails userDetails) {
        if (userRepository.findByUsername(userDetails.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        User user = User.builder()
                .username(userDetails.getUsername())
                .email(userDetails.getEmail())
                .password(passwordEncoder.encode(userDetails.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        return "User registered successfully!";
    }

    @Override
    public String authenticate(AuthRequest authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));

        User user = userRepository.findByEmail(authRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check if the entered password matches the stored password
        if (!passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        UserDetails userDetails = UserDetails.builder().username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .build();

        String token = jwtUtil.generateToken(user);
        return token;
    }

    public String processGoogleUser(OAuth2User oauthUser) {
        String email = oauthUser.getAttribute("email");
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user == null) {
            user = new User();
            user.setEmail(email);
            user.setUsername(oauthUser.getAttribute("name"));
            user.setRole(Role.USER);
            user.setPassword(null);
            userRepository.save(user);
        }

        UserOAuthDetails userOAuthDetails = UserOAuthDetails.builder()
                .username(oauthUser.getAttribute("name"))
                .email(email)
                .build();

        // return jwtTokenProvider.generateToken(user);
        String token = jwtUtil.generateToken(user);
        return token;
    }
}
