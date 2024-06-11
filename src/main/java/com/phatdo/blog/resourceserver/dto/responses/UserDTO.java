package com.phatdo.blog.resourceserver.dto.responses;

import com.phatdo.blog.resourceserver.classification.TypeDTO;

import java.util.List;

public record UserDTO (
        long id,
        String fullName,
        String username,
        List<String> role,
        String participatedDate
) implements TypeDTO{
}
