package com.tharunyetti.IdeaHub.utility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import com.tharunyetti.IdeaHub.entity.User;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    private final String SECRET_KEY = "IDEA+HUB+PROJECT+BY+THARUN+SHARANYA+FULL+STACK+DEVELOPERS";

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());  // Adding User ID
        claims.put("role", user.getRole());  // Adding User Role
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // public String generateToken(UserOAuthDetails userDetails) {
    //     return Jwts.builder()
    //             .setSubject(userDetails.getUsername())
    //             .setIssuedAt(new Date())
    //             .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour
    //             .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
    //             .compact();
    // }

    public Claims extractUsername(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }


    public Long getUserId(String token){
        return extractUsername(token).get("userId", Long.class);
    }

    public String getRole(String token){
        return extractUsername(token).get("role", String.class);
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        return extractUsername(token).equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parser().setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody().getExpiration();
        return expiration.before(new Date());
    }
}
