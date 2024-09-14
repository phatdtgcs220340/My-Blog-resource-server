package com.phatdo.blog.resourceserver.user.dto;

import com.phatdo.blog.resourceserver.user.model.UserRole;
import com.phatdo.blog.resourceserver.utils.commons.dto.TypeDTO;

import java.util.Set;

public record UserDTO (
        long id,
        String fullName,
        String username,
        String avatarUrl,
        Set<UserRole> role,
        String participatedDate
) implements TypeDTO {
}
