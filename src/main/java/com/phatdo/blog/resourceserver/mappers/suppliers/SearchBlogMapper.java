package com.phatdo.blog.resourceserver.mappers.suppliers;

import com.phatdo.blog.resourceserver.dto.responses.SearchBlogDTO;
import com.phatdo.blog.resourceserver.dto.responses.TypeDTO;
import com.phatdo.blog.resourceserver.mappers.DTOMapper;
import com.phatdo.blog.resourceserver.models.blogs.Blog;

public class SearchBlogMapper implements DTOMapper<Blog> {

    @Override
    public TypeDTO toDTO(Blog entity) {
        return new SearchBlogDTO(entity.getTitle(), entity.getId());
    }
}
