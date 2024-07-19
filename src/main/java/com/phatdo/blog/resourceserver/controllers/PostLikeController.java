package com.phatdo.blog.resourceserver.controllers;

import com.phatdo.blog.resourceserver.authentication.UserContext;
import com.phatdo.blog.resourceserver.exception.CustomException;
import com.phatdo.blog.resourceserver.services.BlogLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/post-like")
public class PostLikeController {
    private final BlogLikeService blogLikeService;

    @Autowired
    public PostLikeController(BlogLikeService blogLikeService) {
        this.blogLikeService = blogLikeService;
    }

    @PostMapping("/like/{id}")
    public ResponseEntity<?> postLike(@PathVariable Long id) throws CustomException {
        blogLikeService.like(id, UserContext.getUser());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/unlike/{id}")
    public ResponseEntity<?> unLike(@PathVariable Long id) throws CustomException {
        blogLikeService.unlike(id, UserContext.getUser());
        return ResponseEntity.ok().build();
    }
}
