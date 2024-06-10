package com.phatdo.blog.resourceserver.controllers;

import com.phatdo.blog.resourceserver.authentication.UserContext;
import com.phatdo.blog.resourceserver.classification.TypeDTO;
import com.phatdo.blog.resourceserver.dto.requests.CreateReplyDTO;
import com.phatdo.blog.resourceserver.exception.CustomException;
import com.phatdo.blog.resourceserver.models.Reply;
import com.phatdo.blog.resourceserver.models.User;
import com.phatdo.blog.resourceserver.services.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/reply")
public class ReplyController {
    private final ReplyService replyService;

    @Autowired
    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    @GetMapping
    public ResponseEntity<List<TypeDTO>> findByBlog(@RequestParam(name = "blogId") Long blogId) {
        try {
            return ResponseEntity.ok(replyService.findByBlog(blogId)
                    .stream()
                    .map(Reply::toDTO)
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
            return ResponseEntity.ok(replyService.save(form.content(), user, form.blogId()).toDTO());
        } catch (CustomException e) {
            return new ResponseEntity<>(e.toDTO(), HttpStatusCode.valueOf(e.getCode()));
        }
    }
}
