package com.phatdo.blog.resourceserver.utils.mappers;

import com.phatdo.blog.resourceserver.utils.commons.dto.TypeDTO;
import com.phatdo.blog.resourceserver.utils.mappers.suppliers.*;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DTOMapperFactory {
    private final Map<DTOMapperE, DTOMapper<?, ? extends TypeDTO>> mappers = Map.of(
            DTOMapperE.USER, new UserMapper(),
            DTOMapperE.BLOG, new BlogMapper(),
            DTOMapperE.REPLY, new ReplyMapper(),
            DTOMapperE.PARTIAL_BLOG, new PartialBlogMapper(),
            DTOMapperE.ERROR, new ErrorMapper(),
            DTOMapperE.TAG, new TagMapper(),
            DTOMapperE.FILE_DETAIL, new FileDetailMapper()
    );

    @SuppressWarnings("unchecked")
    public <T> DTOMapper<T, TypeDTO> getMapper(DTOMapperE type) {
        if (mappers.containsKey(type)) {
            return (DTOMapper<T, TypeDTO>) mappers.get(type);
        }
        else throw new IllegalArgumentException("Invalid model: " + type);
    }
}
