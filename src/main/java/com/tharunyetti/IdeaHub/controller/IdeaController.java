package com.tharunyetti.IdeaHub.controller;

import com.tharunyetti.IdeaHub.entity.Idea;
import com.tharunyetti.IdeaHub.service.IdeaService;
import com.tharunyetti.IdeaHub.utility.IdeaDetails;
import com.tharunyetti.IdeaHub.utility.JwtUtil;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/idea")
public class IdeaController {
    
    @Autowired
    private IdeaService ideaService;
    @Autowired
    private JwtUtil jwtUtil;

    private String extractJwtFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwt".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    @PostMapping
    public Idea createIdea(@RequestBody IdeaDetails ideaDetails, HttpServletRequest request) {
        String token = extractJwtFromCookie(request);
        if (token == null) {
            return new Idea();
        }

        return ideaService.createIdea(ideaDetails, token);
    }

    @GetMapping
    public List<Idea> getAllIdeas() {
        return ideaService.getAllIdeas();
    }

    @GetMapping("/{id}")
    public Idea getIdeaById(@PathVariable Long id) {
        return ideaService.getIdeaById(id);
    }

    @GetMapping("/user/{userId}")
    public List<Idea> getIdeasByUser(@PathVariable Long userId) {
        return ideaService.getIdeasByUser(userId);
    }

    @PutMapping("/{id}")
    public Idea updateIdea(@PathVariable Long id, @RequestBody Idea ideaDetails) {
        return ideaService.updateIdea(id, ideaDetails);
    }

    @DeleteMapping("/{id}")
    public String deleteIdea(@PathVariable Long id) {
        ideaService.deleteIdea(id);
        return "Idea deleted successfully";
    }

}
