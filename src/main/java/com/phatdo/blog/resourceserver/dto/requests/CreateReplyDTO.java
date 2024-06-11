package com.phatdo.blog.resourceserver.dto.requests;

public record CreateReplyDTO(
        String content,
        long blogId
) {
}
