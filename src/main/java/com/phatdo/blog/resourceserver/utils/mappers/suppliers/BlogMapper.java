package com.phatdo.blog.resourceserver.utils.mappers.suppliers;

import com.phatdo.blog.resourceserver.blog.dto.FullBlogDTO;
import com.phatdo.blog.resourceserver.utils.mappers.DTOMapper;
import com.phatdo.blog.resourceserver.blog.model.Blog;
import com.phatdo.blog.resourceserver.image.model.Image;
import com.phatdo.blog.resourceserver.tag.model.Tag;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;

public class BlogMapper implements DTOMapper<Blog, FullBlogDTO> {
    @Override
    public FullBlogDTO toDTO(Blog entity) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy - HH:mm a");
        String modifiedDateStr = entity.getModifiedDate().toLocalDateTime().format(formatter);
        return new FullBlogDTO(
                entity.getId(),
                entity.getTitle(),
                entity.getContent(),
                entity.getTags().stream().map(Tag::getName).toList(),
                modifiedDateStr,
                entity.getUser().getId(),
                entity.getUser().getFullName(),
                entity.getLikes().size(),
                entity.getReplies().size(),
                entity.getImages().stream()
                        .sorted(Comparator.comparing(Image::getCreatedAt))
                        .map(Image::getResizedImageUrl)
                        .toList());
    }
}
