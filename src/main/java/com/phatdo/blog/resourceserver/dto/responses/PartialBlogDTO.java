package com.phatdo.blog.resourceserver.dto.responses;

import java.util.List;

public record PartialBlogDTO(
        long id,
        String title,
        List<String> tags,
        String dateAudit,
        List<String> images
) implements TypeDTO{
}
