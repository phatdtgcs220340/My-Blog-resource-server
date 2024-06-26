package com.phatdo.blog.resourceserver.dto.responses;

public record PartialBlogDTO(
        long id,
        String title,
        String type,
        String dateAudit,
        long userId,
        String fullName,
        int totalLike,
        int totalReply
) implements TypeDTO{
}
