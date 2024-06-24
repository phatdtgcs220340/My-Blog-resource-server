package com.phatdo.blog.resourceserver.mappers;

import com.phatdo.blog.resourceserver.dto.responses.TypeDTO;

public interface DTOMapper<T> {
    TypeDTO toDTO(T entity);
}

