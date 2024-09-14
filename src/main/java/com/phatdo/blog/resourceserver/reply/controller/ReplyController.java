package com.phatdo.blog.resourceserver.reply.controller;

import com.phatdo.blog.resourceserver.utils.authentication.UserContext;
import com.phatdo.blog.resourceserver.utils.commons.dto.TypeDTO;
import com.phatdo.blog.resourceserver.reply.request.CreateReplyDTO;
import com.phatdo.blog.resourceserver.reply.request.UpdateReplyDTO;
import com.phatdo.blog.resourceserver.utils.commons.exception.CustomException;
import com.phatdo.blog.resourceserver.utils.mappers.DTOMapperE;
import com.phatdo.blog.resourceserver.utils.mappers.DTOMapperFactory;
import com.phatdo.blog.resourceserver.reply.model.Reply;
import com.phatdo.blog.resourceserver.user.model.User;
import com.phatdo.blog.resourceserver.reply.service.ReplyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.phatdo.blog.resourceserver.utils.commons.path.CommonApi.API_REPLY;

@RestController
@RequestMapping(API_REPLY)
public class ReplyController {
    private final ReplyService replyService;
    private final DTOMapperFactory mapperFactory;

    @Autowired
    public ReplyController(ReplyService replyService, DTOMapperFactory mapperFactory) {
        this.replyService = replyService;
        this.mapperFactory = mapperFactory;
    }

    @GetMapping
    public ResponseEntity<List<TypeDTO>> findByBlog(@RequestParam(name = "blogId") Long blogId) throws CustomException {
        return ResponseEntity.ok(replyService.findByBlog(blogId)
                .stream()
                .map(mapperFactory.getMapper(DTOMapperE.REPLY)::toDTO)
                .collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<TypeDTO> save(@RequestBody @Valid CreateReplyDTO form) throws CustomException {
        User user = UserContext.getUser();
        Reply reply = replyService.save(form.content(), user, form.blogId());
        return ResponseEntity.ok(mapperFactory.getMapper(DTOMapperE.REPLY).toDTO(reply));
    }

    @PatchMapping(path = "{id}")
    public ResponseEntity<TypeDTO> update(@RequestBody @Valid UpdateReplyDTO form,
                                          @PathVariable Long id) throws CustomException {
        Reply reply = replyService
                .updateReply(id, form.newContent(), UserContext.getUser());
        return ResponseEntity.ok(mapperFactory.getMapper(DTOMapperE.REPLY).toDTO(reply));
    }

    @DeleteMapping
    public ResponseEntity<TypeDTO> delete(@RequestParam(name = "id") Long id) throws CustomException {
        replyService.deleteReply(id, UserContext.getUser());
        return ResponseEntity.noContent().build();
    }
}
