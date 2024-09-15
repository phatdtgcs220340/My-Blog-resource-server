package com.phatdo.blog.resourceserver.utils.mappers;

import com.phatdo.blog.resourceserver.utils.commons.dto.TypeDTO;

public interface DTOMapper<T, E extends TypeDTO > {
    E toDTO(T entity);
}

