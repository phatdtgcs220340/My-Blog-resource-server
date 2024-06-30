package com.phatdo.blog.resourceserver.mappers;

import com.phatdo.blog.resourceserver.mappers.suppliers.BlogMapper;
import com.phatdo.blog.resourceserver.mappers.suppliers.ErrorMapper;
import com.phatdo.blog.resourceserver.mappers.suppliers.ReplyMapper;
import com.phatdo.blog.resourceserver.mappers.suppliers.UserMapper;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DTOMapperFactory {
    private final Map<DTOMapperE, DTOMapper<?>> mappers = Map.of(
            DTOMapperE.USER, new UserMapper(),
            DTOMapperE.BLOG, new BlogMapper(),
            DTOMapperE.REPLY, new ReplyMapper(),
            DTOMapperE.ERROR, new ErrorMapper()
    );

    @SuppressWarnings("unchecked")
    public <T> DTOMapper<T> getMapper(DTOMapperE type) {
        if (mappers.containsKey(type)) {
            return (DTOMapper<T>) mappers.get(type);
        }
        else throw new IllegalArgumentException("Invalid model: " + type);
    }
}
