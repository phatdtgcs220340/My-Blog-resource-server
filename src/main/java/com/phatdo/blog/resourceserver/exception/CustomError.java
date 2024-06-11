package com.phatdo.blog.resourceserver.exception;

import lombok.Getter;

@Getter
public enum CustomError {
    BLOG_NOT_FOUND("Cannot find this blog", 404),
    REPLY_NOT_FOUND("Cannot find reply", 404),
    ACCESS_DENIED("Access denied", 403);

    private final String message;
    private final int code;

    CustomError(String message, int code) {
        this.message = message;
        this.code = code;
    }
}
