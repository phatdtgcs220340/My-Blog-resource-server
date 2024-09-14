package com.phatdo.blog.resourceserver.tag.dto;

import com.phatdo.blog.resourceserver.utils.commons.dto.TypeDTO;

public record TagDTO(
        String name
) implements TypeDTO {
}
