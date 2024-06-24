package com.phatdo.blog.resourceserver.dto.responses;

public record ReplyDTO(
        long id,
        String content,
        String updatedDate,
        long userId,
        String username,
        int totalLikes
) implements TypeDTO {
}
