package com.phatdo.blog.resourceserver.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record CreateBlogDTO(
        @NotBlank(message = "The title mustn't be blank")
        String title,
        @NotBlank(message = "The content mustn't be blank")
        String content,
        @NotEmpty(message = "At least one tag")
        List<String> tags,
        List<MultipartFile> files
) {
}
