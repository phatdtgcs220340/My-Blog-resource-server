package com.phatdo.blog.resourceserver.exception;

import lombok.Getter;

@Getter
public class CustomException extends Exception{
    private final int code;

    public CustomException(CustomError error) {
        super(error.getMessage());
        this.code = error.getCode();
    }
}
