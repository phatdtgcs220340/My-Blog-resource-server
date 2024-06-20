package com.phatdo.blog.resourceserver.dto.requests;

import jakarta.validation.constraints.NotBlank;

public record UpdateReplyDTO(
        @NotBlank(message = "The content mustn't be blank")
        String newContent
) {
}
