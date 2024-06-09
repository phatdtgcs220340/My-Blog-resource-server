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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
     * @brief function that save permit the admin save the blog
     * 
     * @param form : CreateBlogDTO type
     * @return TypeDTO
     */
    @PostMapping
    public ResponseEntity<TypeDTO> createBlog(@RequestBody CreateBlogDTO form) {
        User user = UserContext.getUser();
        Blog blog = blogService.saveBlog(form.title(), form.content(), form.type(), user);
        return ResponseEntity.ok(blog.toDTO());
    }

    /**
     * @brief a request that return user a post
     * @param id : post id
     * @return : TypeDTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<TypeDTO> getBlog(@PathVariable long id) {
        try {
            return ResponseEntity.ok(blogService.getBlog(id).toDTO());
        } catch (CustomException e) {
            return new ResponseEntity<>(e.toDTO(), HttpStatusCode.valueOf(e.getCode()));
        }
    }

    /**
     * @brief List of posts with pagination
     * @param page : starting page
     * @param size : size of the page
     * @return : Page of OneBlogDTO
     */
    @GetMapping
    public ResponseEntity<Page<TypeDTO>> getBlogs(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(blogService.getAllBlogs(pageable).map(Blog::toDTO));
    }
}
