package com.phatdo.blog.resourceserver.utils.mappers.suppliers;

import com.phatdo.blog.resourceserver.image.dto.FileDetailDTO;
import com.phatdo.blog.resourceserver.image.model.Image;
import com.phatdo.blog.resourceserver.utils.mappers.DTOMapper;

public class FileDetailMapper implements DTOMapper<Image, FileDetailDTO> {
    @Override
    public FileDetailDTO toDTO(Image entity) {
        return new FileDetailDTO(entity.getId(), entity.getUrl());
    }
}
