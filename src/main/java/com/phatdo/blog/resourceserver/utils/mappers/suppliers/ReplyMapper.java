package com.phatdo.blog.resourceserver.utils.mappers.suppliers;

import com.phatdo.blog.resourceserver.reply.dto.ReplyDTO;
import com.phatdo.blog.resourceserver.utils.mappers.DTOMapper;
import com.phatdo.blog.resourceserver.reply.model.Reply;

import java.time.format.DateTimeFormatter;

public class ReplyMapper implements DTOMapper<Reply, ReplyDTO> {
    @Override
    public ReplyDTO toDTO(Reply entity) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy - HH:mm a");
        String modifiedDateStr = entity.getUpdatedAt().toLocalDateTime().format(formatter);
        return new ReplyDTO(
                entity.getId(),
                entity.getContent(),
                modifiedDateStr,
                entity.getUser().getId(),
                entity.getUser().getFullName(),
                entity.getUser().getAvatarUrl(),
                entity.getLikes().size()
        );
    }
}
