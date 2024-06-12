package com.phatdo.blog.resourceserver.exception;

import com.phatdo.blog.resourceserver.dto.responses.ErrorDTO;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends Exception {
    private final HttpStatus status;

    public CustomException(CustomError error) {
        super(error.getMessage());
        this.status = error.getStatus();
    }

    public ErrorDTO toDTO() {
        return new ErrorDTO(getMessage());
    }
}
