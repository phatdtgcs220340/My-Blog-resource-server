package com.phatdo.blog.resourceserver.dto.responses;


import java.util.List;

public record FullBlogDTO(
        long id,
        String title,
        String content,
        String type,
        String dateAudit,
        long userId,
        String fullName,
        int totalLike,
        int totalReply,
        List<String> images
) implements TypeDTO{
}
