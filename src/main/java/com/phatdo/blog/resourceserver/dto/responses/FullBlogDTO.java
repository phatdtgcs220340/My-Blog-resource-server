package com.phatdo.blog.resourceserver.dto.responses;


public record FullBlogDTO(
        long id,
        String title,
        String content,
        String type,
        String dateAudit,
        long userId,
        String fullName,
        int totalLike,
        int totalReply
) implements TypeDTO{
}
