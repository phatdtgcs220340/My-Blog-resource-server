package com.phatdo.blog.resourceserver.dto.responses;

import com.phatdo.blog.resourceserver.classification.TypeDTO;


public record FullBlogDTO(
        long id,
        String title,
        String content,
        String type,
        String modifiedDateFormatted,
        long userId,
        String fullName,
        int totalLike,
        int totalReply
) implements TypeDTO{
}
