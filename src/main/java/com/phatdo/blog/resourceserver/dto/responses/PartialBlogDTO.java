package com.phatdo.blog.resourceserver.dto.responses;

import com.phatdo.blog.resourceserver.classification.TypeDTO;

public record PartialBlogDTO(
        long id,
        String title,
        String type,
        String modifiedDateFormatted,
        long userId,
        String fullName,
        int totalLike,
        int totalReply
) implements TypeDTO {
}
