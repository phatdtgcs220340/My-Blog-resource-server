package com.phatdo.blog.resourceserver.utils.mappers;

import com.phatdo.blog.resourceserver.utils.commons.dto.TypeDTO;
import com.phatdo.blog.resourceserver.utils.mappers.suppliers.*;
import lombok.Getter;

@Getter
public enum DTOMapperE {
    BLOG(new BlogMapper()),
    USER(new UserMapper()),
    REPLY(new ReplyMapper()),
    PARTIAL_BLOG(new PartialBlogMapper()),
    ERROR(new ErrorMapper()),
    TAG(new TagMapper()),
    FILE_DETAIL(new FileDetailMapper());

    private final DTOMapper<?, ? extends TypeDTO> mapper;

    DTOMapperE(DTOMapper<?, ? extends TypeDTO> mapper) {
        this.mapper = mapper;
    }
}
