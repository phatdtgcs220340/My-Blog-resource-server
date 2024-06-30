package com.phatdo.blog.resourceserver.mappers;

import org.springframework.stereotype.Component;

@Component
public class DTOMapperFactory {
    @SuppressWarnings("unchecked")
    public <T> DTOMapper<T> getMapper(String model) {
        return (DTOMapper<T>) switch (model) {
            case "user" -> new UserMapper();
            case "blog" -> new BlogMapper();
            case "reply" -> new ReplyMapper();
            case "error" -> new ErrorMapper();
            default -> throw new IllegalArgumentException("Unknown model: " + model);
        };
    }
}
