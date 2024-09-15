package com.phatdo.blog.resourceserver.image.dto;

import com.phatdo.blog.resourceserver.utils.commons.dto.TypeDTO;

public record FileDetailDTO(
        Long id,
        String fileLink
) implements TypeDTO {
}
