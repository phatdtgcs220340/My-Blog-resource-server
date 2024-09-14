package com.phatdo.blog.resourceserver.reply.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateReplyDTO(
        @NotBlank(message = "The content mustn't be blank")
        String newContent
) {
}
