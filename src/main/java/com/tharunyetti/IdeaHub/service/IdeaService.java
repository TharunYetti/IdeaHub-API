package com.tharunyetti.IdeaHub.service;

import com.tharunyetti.IdeaHub.entity.Idea;
import com.tharunyetti.IdeaHub.utility.IdeaDetails;

import java.util.List;

public interface IdeaService {
    Idea createIdea(IdeaDetails idea, String token);
    List<Idea> getAllIdeas();
    Idea getIdeaById(Long id);
    List<Idea> getIdeasByUser(Long userId);

    Idea updateIdea(Long id, Idea ideaDetails) ;

    void deleteIdea(Long id);
}
