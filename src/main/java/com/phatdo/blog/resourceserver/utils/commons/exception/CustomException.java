package com.phatdo.blog.resourceserver.utils.commons.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends Exception {
    private final HttpStatus status;

    public CustomException(CustomError error) {
        super(error.getMessage());
        this.status = error.getStatus();
    }
}
