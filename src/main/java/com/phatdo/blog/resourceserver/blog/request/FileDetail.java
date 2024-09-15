package com.phatdo.blog.resourceserver.blog.request;

import jakarta.validation.constraints.NotNull;

public record FileDetail(
        @NotNull
        Long id,
        String description) {
}
