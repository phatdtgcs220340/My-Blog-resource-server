package com.phatdo.blog.resourceserver.mappers;

import com.phatdo.blog.resourceserver.classification.TypeDTO;
import com.phatdo.blog.resourceserver.dto.responses.ErrorDTO;
import com.phatdo.blog.resourceserver.exception.CustomException;

public class ErrorMapper implements DTOMapper<CustomException>{
    @Override
    public TypeDTO toDTO(CustomException entity) {
        return new ErrorDTO(entity.getMessage());
    }
}
