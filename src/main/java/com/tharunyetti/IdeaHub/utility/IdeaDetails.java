package com.tharunyetti.IdeaHub.utility;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IdeaDetails {
    private String title;
    private String description;
    private String image;
}
