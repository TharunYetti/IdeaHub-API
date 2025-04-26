package com.tharunyetti.IdeaHub.service;

import com.tharunyetti.IdeaHub.entity.Idea;
import com.tharunyetti.IdeaHub.repository.IdeaRepository;
import com.tharunyetti.IdeaHub.utility.IdeaDetails;
import com.tharunyetti.IdeaHub.utility.JwtUtil;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class IdeaServiceImpl implements IdeaService{
    @Autowired
    private IdeaRepository ideaRepository;
    @Autowired
    private JwtUtil jwtUtil;

    // private String extractJwtFromCookie(HttpServletRequest request) {
    //     Cookie[] cookies = request.getCookies();
    //     if (cookies != null) {
    //         for (Cookie cookie : cookies) {
    //             if ("jwt".equals(cookie.getName())) { // Match cookie name
    //                 return cookie.getValue();
    //             }
    //         }
    //     }
    //     return null;
    // }

    public Idea createIdea(IdeaDetails ideaDetails, String token){
        // MultipartFile imageFile = ideaDetails.getImage();
        // byte[] imageToStore = null;
        // try{
        //     if(imageFile!=null && !imageFile.isEmpty()){
        //         imageToStore = imageFile.getBytes();
        //    }
        // }catch(IOException e){
        //     imageToStore = null;
        // }
        Idea idea = Idea.builder()
        .title(ideaDetails.getTitle())
        .description(ideaDetails.getDescription())
        .image(ideaDetails.getImage())
        .createdBy(jwtUtil.getUserId(token))
        .build();
        return ideaRepository.save(idea);
    }

    public List<Idea> getAllIdeas() {
        return ideaRepository.findAll();
    }

    public Idea getIdeaById(Long id) {
        return ideaRepository.findById(id).orElseThrow(() -> new RuntimeException("Idea not found"));
    }

    public List<Idea> getIdeasByUser(Long userId) {
        return ideaRepository.findByCreatedBy(userId);
    }

    public Idea updateIdea(Long id, Idea ideaDetails) {
        Idea idea = getIdeaById(id);
        idea.setTitle(ideaDetails.getTitle());
        idea.setDescription(ideaDetails.getDescription());
        return ideaRepository.save(idea);
    }

    public void deleteIdea(Long id) {
        Idea idea = getIdeaById(id);
        ideaRepository.delete(idea);
    }
}
