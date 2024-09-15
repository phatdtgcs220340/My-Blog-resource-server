package com.phatdo.blog.resourceserver.utils.mappers.suppliers;

import com.phatdo.blog.resourceserver.utils.commons.dto.ErrorDTO;
import com.phatdo.blog.resourceserver.utils.commons.exception.CustomException;
import com.phatdo.blog.resourceserver.utils.mappers.DTOMapper;

public class ErrorMapper implements DTOMapper<CustomException, ErrorDTO> {
    @Override
    public ErrorDTO toDTO(CustomException entity) {
        return new ErrorDTO(entity.getMessage());
    }
}
