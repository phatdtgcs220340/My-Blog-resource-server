package com.phatdo.blog.resourceserver.blog.dto;

import com.phatdo.blog.resourceserver.utils.commons.dto.TypeDTO;

public record SearchBlogDTO(
        String title,
        Long id
) implements TypeDTO {
}
