package com.phatdo.blog.resourceserver.controllers;

import com.phatdo.blog.resourceserver.authentication.UserContext;
import com.phatdo.blog.resourceserver.dto.responses.TypeDTO;
import com.phatdo.blog.resourceserver.dto.requests.CreateBlogDTO;
import com.phatdo.blog.resourceserver.dto.requests.UpdateBlogDTO;
import com.phatdo.blog.resourceserver.exception.CustomException;
import com.phatdo.blog.resourceserver.mappers.DTOMapperE;
import com.phatdo.blog.resourceserver.mappers.DTOMapperFactory;
import com.phatdo.blog.resourceserver.models.blogs.Blog;
import com.phatdo.blog.resourceserver.services.BlogService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/blog")
public class BlogController {
    private final BlogService blogService;
    private final DTOMapperFactory mapperFactory;

    @Autowired
    public BlogController(BlogService blogService, DTOMapperFactory mapperFactory) {
        this.blogService = blogService;
        this.mapperFactory = mapperFactory;
    }

    @PostMapping
    public ResponseEntity<TypeDTO> createBlog(@RequestBody @Valid CreateBlogDTO form) {
        Blog blog = blogService.saveBlog(form.title(), form.content(), form.type(), UserContext.getUser());
        return ResponseEntity.ok(mapperFactory.getMapper(DTOMapperE.BLOG).toDTO(blog));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TypeDTO> getBlog(@PathVariable long id) throws CustomException {
        Blog blog = blogService.getBlog(id);
        return ResponseEntity.ok(mapperFactory.getMapper(DTOMapperE.BLOG).toDTO(blog));
    }

    @GetMapping
    public ResponseEntity<Page<TypeDTO>> getBlogs(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(blogService.getAllBlogs(pageable).map(mapperFactory.getMapper(DTOMapperE.PARTIAL_BLOG)::toDTO));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TypeDTO> updateBlog(@RequestBody @Valid UpdateBlogDTO form,
                                              @PathVariable long id) throws CustomException {
        Blog blog = blogService.updateBlog(
                id,
                form.title(),
                form.content());
        return ResponseEntity.ok(mapperFactory.getMapper(DTOMapperE.BLOG).toDTO(blog));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TypeDTO> deleteBlog(@PathVariable long id) throws CustomException {
        blogService.deleteBlog(id, UserContext.getUser());
        return ResponseEntity.noContent().build();
    }
}
