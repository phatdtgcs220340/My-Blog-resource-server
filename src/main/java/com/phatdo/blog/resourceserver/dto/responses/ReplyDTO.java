package com.phatdo.blog.resourceserver.dto.responses;

import com.phatdo.blog.resourceserver.classification.TypeDTO;

public record ReplyDTO(
        long id,
        String content,
        String updatedDate,
        long userId,
        String username,
        int totalLikes
) implements TypeDTO {
}
