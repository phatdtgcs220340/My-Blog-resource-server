package com.phatdo.blog.resourceserver.controllers;

import com.phatdo.blog.resourceserver.authentication.UserContext;
import com.phatdo.blog.resourceserver.classification.TypeDTO;
import com.phatdo.blog.resourceserver.dto.requests.CreateReplyDTO;
import com.phatdo.blog.resourceserver.dto.requests.UpdateReplyDTO;
import com.phatdo.blog.resourceserver.exception.CustomException;
import com.phatdo.blog.resourceserver.mappers.DTOMapper;
import com.phatdo.blog.resourceserver.mappers.ErrorMapper;
import com.phatdo.blog.resourceserver.mappers.ReplyMapper;
import com.phatdo.blog.resourceserver.models.Reply;
import com.phatdo.blog.resourceserver.models.User;
import com.phatdo.blog.resourceserver.services.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/reply")
public class ReplyController {
    private final ReplyService replyService;
    private final DTOMapper<Reply> mapper = new ReplyMapper();
    private final DTOMapper<CustomException> errorMapper = new ErrorMapper();

    @Autowired
    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    @GetMapping
    public ResponseEntity<List<TypeDTO>> findByBlog(@RequestParam(name = "blogId") Long blogId) {
        try {
            return ResponseEntity.ok(replyService.findByBlog(blogId)
                    .stream()
                    .map(mapper::toDTO)
                    .collect(Collectors.toList()));
        }
        catch (CustomException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<TypeDTO> save(@RequestBody CreateReplyDTO form) {
        try {
            User user = UserContext.getUser();
            Reply reply = replyService.save(form.content(), user, form.blogId());
            return ResponseEntity.ok(mapper.toDTO(reply));
        } catch (CustomException e) {
            return new ResponseEntity<>(errorMapper.toDTO(e), e.getStatus());
        }
    }

    @PatchMapping(path = "{id}")
    public ResponseEntity<TypeDTO> update(@RequestBody UpdateReplyDTO form,
                                          @PathVariable Long id) {
        try {
            Reply reply = replyService
                    .updateReply(id, form.newContent(), UserContext.getUser());
            return ResponseEntity.ok(mapper.toDTO(reply));
        }
        catch (CustomException e) {
            return new ResponseEntity<>(errorMapper.toDTO(e), e.getStatus());
        }
    }

    @DeleteMapping
    public ResponseEntity<TypeDTO> delete(@RequestParam(name = "id") Long id) {
        try {
            replyService.deleteReply(id, UserContext.getUser());
            return ResponseEntity.noContent().build();
        } catch (CustomException e) {
            return new ResponseEntity<>(errorMapper.toDTO(e), e.getStatus());
        }
    }
}
