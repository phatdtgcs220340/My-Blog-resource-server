package com.phatdo.blog.resourceserver.exception;

import com.phatdo.blog.resourceserver.classification.TypeDTO;
import com.phatdo.blog.resourceserver.dto.responses.ErrorDTO;
import lombok.Getter;

@Getter
public class CustomException extends Exception {
    private final int code;

    public CustomException(CustomError error) {
        super(error.getMessage());
        this.code = error.getCode();
    }

    public ErrorDTO toDTO() {
        return new ErrorDTO(getMessage());
    }
}
