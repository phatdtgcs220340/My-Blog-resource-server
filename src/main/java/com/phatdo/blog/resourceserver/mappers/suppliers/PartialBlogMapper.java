package com.phatdo.blog.resourceserver.mappers.suppliers;

import com.phatdo.blog.resourceserver.dto.responses.PartialBlogDTO;
import com.phatdo.blog.resourceserver.dto.responses.TypeDTO;
import com.phatdo.blog.resourceserver.mappers.DTOMapper;
import com.phatdo.blog.resourceserver.models.blogs.Blog;
import com.phatdo.blog.resourceserver.models.images.Image;

import java.time.format.DateTimeFormatter;

public class PartialBlogMapper implements DTOMapper<Blog> {
    @Override
    public TypeDTO toDTO(Blog entity) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy - HH:mm a");
        String modifiedDateStr = entity.getModifiedDate().toLocalDateTime().format(formatter);
        return new PartialBlogDTO(
                entity.getId(),
                entity.getTitle(),
                entity.getBlogType().toString(),
                modifiedDateStr,
                entity.getImages().stream().map(Image::getUrl).toList());
    }
}
