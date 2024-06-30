package com.phatdo.blog.resourceserver.dto.responses;

public record ReplyDTO(
        long id,
        String content,
        String dateAudit,
        long userId,
        String username,
        int totalLikes
) implements TypeDTO {
}
