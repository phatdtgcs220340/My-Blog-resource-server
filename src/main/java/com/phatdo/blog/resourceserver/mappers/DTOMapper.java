package com.phatdo.blog.resourceserver.mappers;

import com.phatdo.blog.resourceserver.classification.TypeDTO;

public interface DTOMapper<T> {
    TypeDTO toDTO(T entity);
}

