package com.phatdo.blog.resourceserver.dto.requests;

import com.phatdo.blog.resourceserver.models.blogs.BlogType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record CreateBlogDTO(
        @NotBlank(message = "The title mustn't be blank")
        String title,
        @NotBlank(message = "The content mustn't be blank")
        String content,
        @NotNull(message = "The type mustn't be null")
        BlogType type,
        List<MultipartFile> files
) {
}
