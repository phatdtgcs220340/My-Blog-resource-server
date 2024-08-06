package com.phatdo.blog.resourceserver.dto.responses;

import com.phatdo.blog.resourceserver.models.users.UserRole;

import java.util.Set;

public record UserDTO (
        long id,
        String fullName,
        String username,
        String avatarUrl,
        Set<UserRole> role,
        String participatedDate
) implements TypeDTO{
}
