package com.phatdo.blog.resourceserver.utils.mappers.suppliers;

import com.phatdo.blog.resourceserver.blog.dto.PartialBlogDTO;
import com.phatdo.blog.resourceserver.utils.commons.dto.TypeDTO;
import com.phatdo.blog.resourceserver.utils.mappers.DTOMapper;
import com.phatdo.blog.resourceserver.blog.model.Blog;
import com.phatdo.blog.resourceserver.image.model.Image;
import com.phatdo.blog.resourceserver.tag.model.Tag;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;

public class PartialBlogMapper implements DTOMapper<Blog> {
    @Override
    public TypeDTO toDTO(Blog entity) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy - HH:mm a");
        String modifiedDateStr = entity.getModifiedDate().toLocalDateTime().format(formatter);
        return new PartialBlogDTO(
                entity.getId(),
                entity.getTitle(),
                entity.getTags().stream().map(Tag::getName).toList(),
                modifiedDateStr,
                entity.getImages().stream()
                        .sorted(Comparator.comparing(Image::getCreatedAt))
                        .map(Image::getUrl)
                        .toList());
    }
}
