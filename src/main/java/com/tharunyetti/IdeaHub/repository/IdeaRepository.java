package com.tharunyetti.IdeaHub.repository;

import com.tharunyetti.IdeaHub.entity.Idea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IdeaRepository extends JpaRepository<Idea, Long> {
    List<Idea> findByCreatedBy(Long userId); // Fetch ideas by user ID
}
