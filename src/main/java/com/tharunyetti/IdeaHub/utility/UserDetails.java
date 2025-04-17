package com.tharunyetti.IdeaHub.utility;

import com.tharunyetti.IdeaHub.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetails {
    private String username;
    private String password;
    private Role role;
    private String email;
}
