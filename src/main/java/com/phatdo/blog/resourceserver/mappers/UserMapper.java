package com.phatdo.blog.resourceserver.mappers;

import com.phatdo.blog.resourceserver.dto.responses.UserDTO;
import com.phatdo.blog.resourceserver.models.User;

import java.time.format.DateTimeFormatter;

public class UserMapper implements  DTOMapper<User> {
    @Override
    public UserDTO toDTO(User entity) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy - HH:mm a");
        String modifiedDateStr = entity.getParticipatedDate().toLocalDateTime().format(formatter);
        return new UserDTO(
                entity.getId(),
                entity.getFullName(),
                entity.getUsername(),
                entity.getRoles()
                        .stream()
                        .map(Enum::toString)
                        .toList(),
                modifiedDateStr);
    }
}
