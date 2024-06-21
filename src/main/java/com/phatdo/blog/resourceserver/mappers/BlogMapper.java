package com.phatdo.blog.resourceserver.mappers;

import com.phatdo.blog.resourceserver.classification.TypeDTO;
import com.phatdo.blog.resourceserver.dto.responses.FullBlogDTO;
import com.phatdo.blog.resourceserver.models.Blog;

import java.time.format.DateTimeFormatter;

public class BlogMapper implements DTOMapper<Blog>{
    @Override
    public TypeDTO toDTO(Blog entity) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy - HH:mm a");
        String modifiedDateStr = entity.getModifiedDate().toLocalDateTime().format(formatter);
        return new FullBlogDTO(
                entity.getId(),
                entity.getTitle(),
                entity.getContent(),
                entity.getBlogType().toString(),
                modifiedDateStr,
                entity.getUser().getId(),
                entity.getUser().getFullName(),
                entity.getLikes().size(),
                entity.getReplies().size());
    }
}
