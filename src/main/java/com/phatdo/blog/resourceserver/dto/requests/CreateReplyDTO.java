package com.phatdo.blog.resourceserver.dto.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record CreateReplyDTO(
        @NotBlank(message = "The reply content mustn't be blank")
        String content,
        @Min(value = 0, message = "The blog id is invalid")
        long blogId
) {
}
