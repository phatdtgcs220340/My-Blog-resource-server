package com.phatdo.blog.resourceserver.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum CustomError {
    BLOG_NOT_FOUND("Cannot find this blog", HttpStatus.NOT_FOUND),
    REPLY_NOT_FOUND("Cannot find reply", HttpStatus.NOT_FOUND),
    INVALID_FORM("Invalid form", HttpStatus.BAD_REQUEST),
    ACCESS_DENIED("Access denied", HttpStatus.FORBIDDEN),
    INVALID_FILE_CONTENT_TYPE("Invalid content type", HttpStatus.BAD_REQUEST),
    DUPLICATED_LIKE("Duplicated like", HttpStatus.CONFLICT),
    LIKE_NOT_FOUND("Like not found", HttpStatus.NOT_FOUND),;

    private final String message;
    private final HttpStatus status;

    CustomError(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
