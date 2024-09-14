package com.phatdo.blog.resourceserver.utils.mappers.suppliers;

import com.phatdo.blog.resourceserver.tag.dto.TagDTO;
import com.phatdo.blog.resourceserver.utils.commons.dto.TypeDTO;
import com.phatdo.blog.resourceserver.utils.mappers.DTOMapper;
import com.phatdo.blog.resourceserver.tag.model.Tag;

public class TagMapper implements DTOMapper<Tag> {
    @Override
    public TypeDTO toDTO(Tag entity) {
        return new TagDTO(entity.getName());
    }
}
