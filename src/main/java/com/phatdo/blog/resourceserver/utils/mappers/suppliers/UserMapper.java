package com.phatdo.blog.resourceserver.utils.mappers.suppliers;

import com.phatdo.blog.resourceserver.user.dto.UserDTO;
import com.phatdo.blog.resourceserver.utils.mappers.DTOMapper;
import com.phatdo.blog.resourceserver.user.model.User;

import java.time.format.DateTimeFormatter;

public class UserMapper implements DTOMapper<User, UserDTO> {
    @Override
    public UserDTO toDTO(User entity) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy - HH:mm a");
        String modifiedDateStr = entity.getParticipatedDate().toLocalDateTime().format(formatter);
        return new UserDTO(
                entity.getId(),
                entity.getFullName(),
                entity.getUsername(),
                entity.getAvatarUrl(),
                entity.getRoles(),
                modifiedDateStr);
    }
}
