package com.phatdo.blog.resourceserver.dto.requests;

public record UpdateReplyDTO(
        Long id,
        String newContent
) {
}
