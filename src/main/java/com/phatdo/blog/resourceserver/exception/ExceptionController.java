package com.phatdo.blog.resourceserver.exception;

import com.phatdo.blog.resourceserver.dto.responses.ErrorDTO;
import com.phatdo.blog.resourceserver.dto.responses.TypeDTO;
import com.phatdo.blog.resourceserver.mappers.DTOMapperE;
import com.phatdo.blog.resourceserver.mappers.DTOMapperFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class ExceptionController {
    private final DTOMapperFactory mapperFactory;

    public ExceptionController(DTOMapperFactory mapperFactory) {
        this.mapperFactory = mapperFactory;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<TypeDTO> customExceptionHandler(CustomException exception) {
        return new ResponseEntity<>(mapperFactory.getMapper(DTOMapperE.ERROR).toDTO(exception), exception.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<TypeDTO> exceptionHandler(Exception exception) {
        return ResponseEntity.internalServerError().body(new ErrorDTO(exception.getMessage()));
    }
}
