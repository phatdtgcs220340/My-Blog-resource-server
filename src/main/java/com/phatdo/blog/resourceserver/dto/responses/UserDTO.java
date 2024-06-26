package com.phatdo.blog.resourceserver.dto.responses;

import java.util.List;

public record UserDTO (
        long id,
        String fullName,
        String username,
        List<String> role,
        String participatedDate
) implements TypeDTO{
}
