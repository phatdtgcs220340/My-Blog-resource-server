package com.phatdo.blog.resourceserver.utils.mappers.suppliers;

import com.phatdo.blog.resourceserver.utils.commons.dto.TypeDTO;
import com.phatdo.blog.resourceserver.utils.commons.dto.ErrorDTO;
import com.phatdo.blog.resourceserver.utils.commons.exception.CustomException;
import com.phatdo.blog.resourceserver.utils.mappers.DTOMapper;

public class ErrorMapper implements DTOMapper<CustomException> {
    @Override
    public TypeDTO toDTO(CustomException entity) {
        return new ErrorDTO(entity.getMessage());
    }
}
