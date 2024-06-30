package com.phatdo.blog.resourceserver.mappers.suppliers;

import com.phatdo.blog.resourceserver.dto.responses.TypeDTO;
import com.phatdo.blog.resourceserver.dto.responses.ErrorDTO;
import com.phatdo.blog.resourceserver.exception.CustomException;
import com.phatdo.blog.resourceserver.mappers.DTOMapper;

public class ErrorMapper implements DTOMapper<CustomException> {
    @Override
    public TypeDTO toDTO(CustomException entity) {
        return new ErrorDTO(entity.getMessage());
    }
}
