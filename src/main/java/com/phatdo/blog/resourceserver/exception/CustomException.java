package com.phatdo.blog.resourceserver.exception;

import com.phatdo.blog.resourceserver.classification.TypeDTO;
import lombok.Getter;

@Getter
public class CustomException extends Exception implements TypeDTO {
    private final int code;

    public CustomException(CustomError error) {
        super(error.getMessage());
        this.code = error.getCode();
    }

    @Override
    public String type() {
        return "error-dto";
    }
}
