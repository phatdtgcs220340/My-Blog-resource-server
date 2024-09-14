package com.phatdo.blog.resourceserver.reply.dto;

import com.phatdo.blog.resourceserver.utils.commons.dto.TypeDTO;

public record ReplyDTO(
        long id,
        String content,
        String dateAudit,
        long userId,
        String username,
        String avatarUrl,
        int totalLikes
) implements TypeDTO {
}
