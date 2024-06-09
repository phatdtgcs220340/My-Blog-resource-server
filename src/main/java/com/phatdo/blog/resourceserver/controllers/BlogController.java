package com.phatdo.blog.resourceserver.controllers;

import com.phatdo.blog.resourceserver.authentication.UserContext;
import com.phatdo.blog.resourceserver.classification.TypeDTO;
import com.phatdo.blog.resourceserver.dto.requests.CreateBlogDTO;
import com.phatdo.blog.resourceserver.dto.responses.OneBlogDTO;
import com.phatdo.blog.resourceserver.exception.CustomException;
import com.phatdo.blog.resourceserver.models.Blog;
import com.phatdo.blog.resourceserver.models.User;
import com.phatdo.blog.resourceserver.services.BlogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/blog")
public class BlogController {
    private final BlogService blogService;

    @Autowired
    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    /**
     * a function that save permit the admin save the blog
     * 
     * @param form : CreateBlogDTO type
     * @return OneBlogDTO, a response dto
     */
    @PostMapping
    public ResponseEntity<OneBlogDTO> createBlog(@RequestBody CreateBlogDTO form) {
        User user = UserContext.getUser();
        Blog blog = blogService.saveBlog(form.title(), form.content(), user);
        return ResponseEntity.ok(blog.toDTO());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TypeDTO> getBlog(@PathVariable long id) {
        try {
            return ResponseEntity.ok(blogService.getBlog(id).toDTO());
        } catch (CustomException e) {
            return new ResponseEntity<>(e, HttpStatusCode.valueOf(e.getCode()));
        }
    }
}
