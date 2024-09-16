package com.phatdo.blog.resourceserver.utils.mappers;

import com.phatdo.blog.resourceserver.utils.commons.dto.TypeDTO;
import com.phatdo.blog.resourceserver.utils.mappers.suppliers.*;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DTOMapperFactory {
    @SuppressWarnings("unchecked")
    public <T> DTOMapper<T, TypeDTO> getMapper(DTOMapperE type) {
       return (DTOMapper<T, TypeDTO>) type.getMapper();
    }
}
