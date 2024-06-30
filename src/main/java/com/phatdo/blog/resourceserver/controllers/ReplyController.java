package com.phatdo.blog.resourceserver.controllers;

import com.phatdo.blog.resourceserver.authentication.UserContext;
import com.phatdo.blog.resourceserver.dto.responses.TypeDTO;
import com.phatdo.blog.resourceserver.dto.requests.CreateReplyDTO;
import com.phatdo.blog.resourceserver.dto.requests.UpdateReplyDTO;
import com.phatdo.blog.resourceserver.exception.CustomException;
import com.phatdo.blog.resourceserver.mappers.DTOMapperFactory;
import com.phatdo.blog.resourceserver.models.replies.Reply;
import com.phatdo.blog.resourceserver.models.users.User;
import com.phatdo.blog.resourceserver.services.ReplyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/reply")
public class ReplyController {
    private final ReplyService replyService;
    private final DTOMapperFactory mapperFactory;

    @Autowired
    public ReplyController(ReplyService replyService, DTOMapperFactory mapperFactory) {
        this.replyService = replyService;
        this.mapperFactory = mapperFactory;
    }

    @GetMapping
    public ResponseEntity<List<TypeDTO>> findByBlog(@RequestParam(name = "blogId") Long blogId) {
        try {
            return ResponseEntity.ok(replyService.findByBlog(blogId)
                    .stream()
                    .map(mapperFactory.getMapper("reply")::toDTO)
                    .collect(Collectors.toList()));
        }
        catch (CustomException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<TypeDTO> save(@RequestBody @Valid CreateReplyDTO form) {
        try {
            User user = UserContext.getUser();
            Reply reply = replyService.save(form.content(), user, form.blogId());
            return ResponseEntity.ok(mapperFactory.getMapper("reply").toDTO(reply));
        } catch (CustomException e) {
            return new ResponseEntity<>(mapperFactory.getMapper("error").toDTO(e), e.getStatus());
        }
    }

    @PatchMapping(path = "{id}")
    public ResponseEntity<TypeDTO> update(@RequestBody @Valid UpdateReplyDTO form,
                                          @PathVariable Long id) {
        try {
            Reply reply = replyService
                    .updateReply(id, form.newContent(), UserContext.getUser());
            return ResponseEntity.ok(mapperFactory.getMapper("reply").toDTO(reply));
        }
        catch (CustomException e) {
            return new ResponseEntity<>(mapperFactory.getMapper("error").toDTO(e), e.getStatus());
        }
    }

    @DeleteMapping
    public ResponseEntity<TypeDTO> delete(@RequestParam(name = "id") Long id) {
        try {
            replyService.deleteReply(id, UserContext.getUser());
            return ResponseEntity.noContent().build();
        } catch (CustomException e) {
            return new ResponseEntity<>(mapperFactory.getMapper("error").toDTO(e), e.getStatus());
        }
    }
}
