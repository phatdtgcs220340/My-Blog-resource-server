package com.phatdo.blog.resourceserver.mappers.suppliers;

import com.phatdo.blog.resourceserver.dto.responses.TagDTO;
import com.phatdo.blog.resourceserver.dto.responses.TypeDTO;
import com.phatdo.blog.resourceserver.mappers.DTOMapper;
import com.phatdo.blog.resourceserver.models.tags.Tag;

public class TagMapper implements DTOMapper<Tag> {
    @Override
    public TypeDTO toDTO(Tag entity) {
        return new TagDTO(entity.getName());
    }
}
