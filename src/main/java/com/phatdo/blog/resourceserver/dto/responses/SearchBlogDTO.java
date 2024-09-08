package com.phatdo.blog.resourceserver.dto.responses;

public record SearchBlogDTO(
        String title,
        Long id
) implements TypeDTO {
}
