package com.phatdo.blog.resourceserver.dto.responses;

import com.phatdo.blog.resourceserver.classification.TypeDTO;

import java.util.List;

public record OneBlogDTO (
        long id,
        String title,
        String content,
        String modifiedDateFormatted,
        long userId,
        String fullName,
        int totalLike,
        int totalReply
) implements TypeDTO{
    public String type() {
        return "success-dto";
    }
}
