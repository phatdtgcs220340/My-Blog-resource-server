package com.phatdo.blog.resourceserver.blog.dto;

import com.phatdo.blog.resourceserver.utils.commons.dto.TypeDTO;

import java.util.List;

public record PartialBlogDTO(
        long id,
        String title,
        List<String> tags,
        String dateAudit,
        List<String> images
) implements TypeDTO {
}
