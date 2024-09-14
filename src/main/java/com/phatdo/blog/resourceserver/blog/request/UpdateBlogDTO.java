package com.phatdo.blog.resourceserver.blog.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateBlogDTO (
        @NotBlank(message = "The title mustn't be blank")
        String title,
        @NotBlank(message = "The title mustn't be blank")
        String content) {
}
