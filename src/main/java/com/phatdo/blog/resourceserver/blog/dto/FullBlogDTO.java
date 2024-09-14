package com.phatdo.blog.resourceserver.blog.dto;


import com.phatdo.blog.resourceserver.utils.commons.dto.TypeDTO;

import java.util.List;

public record FullBlogDTO(
        long id,
        String title,
        String content,
        List<String> tags,
        String dateAudit,
        long userId,
        String fullName,
        int totalLike,
        int totalReply,
        List<String> images
) implements TypeDTO {
}
