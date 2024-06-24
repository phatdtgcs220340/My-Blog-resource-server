package com.phatdo.blog.resourceserver.mappers;

import com.phatdo.blog.resourceserver.dto.responses.TypeDTO;
import com.phatdo.blog.resourceserver.dto.responses.ReplyDTO;
import com.phatdo.blog.resourceserver.models.replies.Reply;

import java.time.format.DateTimeFormatter;

public class ReplyMapper implements DTOMapper<Reply>{
    @Override
    public TypeDTO toDTO(Reply entity) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy - HH:mm a");
        String modifiedDateStr = entity.getUpdatedAt().toLocalDateTime().format(formatter);
        return new ReplyDTO(
                entity.getId(),
                entity.getContent(),
                modifiedDateStr,
                entity.getUser().getId(),
                entity.getUser().getFullName(),
                entity.getLikes().size()
        );
    }
}
