package com.phatdo.blog.resourceserver.blog.controller;

import com.phatdo.blog.resourceserver.utils.authentication.UserContext;
import com.phatdo.blog.resourceserver.blog.request.BlogFilter;
import com.phatdo.blog.resourceserver.utils.commons.dto.TypeDTO;
import com.phatdo.blog.resourceserver.blog.request.CreateBlogDTO;
import com.phatdo.blog.resourceserver.blog.request.UpdateBlogDTO;
import com.phatdo.blog.resourceserver.utils.commons.exception.CustomException;
import com.phatdo.blog.resourceserver.utils.mappers.DTOMapperE;
import com.phatdo.blog.resourceserver.utils.mappers.DTOMapperFactory;
import com.phatdo.blog.resourceserver.blog.model.Blog;
import com.phatdo.blog.resourceserver.image.model.Image;
import com.phatdo.blog.resourceserver.blog.service.BlogService;

import com.phatdo.blog.resourceserver.image.service.S3Service;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.phatdo.blog.resourceserver.utils.commons.path.CommonApi.API_BLOG;

@RestController
@RequestMapping(API_BLOG)
public class BlogController {
    private final BlogService blogService;
    private final DTOMapperFactory mapperFactory;
    private final S3Service imageUploadService;

    @Autowired
    public BlogController(BlogService blogService, DTOMapperFactory mapperFactory, S3Service imageUploadService) {
        this.blogService = blogService;
        this.mapperFactory = mapperFactory;
        this.imageUploadService = imageUploadService;
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<TypeDTO> createBlog(@ModelAttribute @Valid CreateBlogDTO form) throws Exception {
        Blog blog = blogService.saveBlog(form, UserContext.getUser());
        if (form.files() != null && !form.files().isEmpty()) {
            CompletableFuture<List<Image>> future = imageUploadService.upload(blog, form.files());
            blog.getImages().addAll(future.join());
        }
        return ResponseEntity.ok(mapperFactory.getMapper(DTOMapperE.BLOG).toDTO(blog));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TypeDTO> getBlog(@PathVariable long id) throws CustomException {
        Blog blog = blogService.getBlog(id);
        return ResponseEntity.ok(mapperFactory.getMapper(DTOMapperE.BLOG).toDTO(blog));
    }

    @PostMapping("/list")
    public ResponseEntity<Page<TypeDTO>> getBlogs(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "5") int size,
                                                  @RequestBody BlogFilter filter) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(blogService.getAllBlogs(filter, pageable).map(mapperFactory.getMapper(DTOMapperE.PARTIAL_BLOG)::toDTO));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TypeDTO> updateBlog(@RequestBody @Valid UpdateBlogDTO form,
                                              @PathVariable long id) throws CustomException {
        Blog blog = blogService.updateBlog(id, form);
        return ResponseEntity.ok(mapperFactory.getMapper(DTOMapperE.BLOG).toDTO(blog));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TypeDTO> deleteBlog(@PathVariable long id) throws CustomException {
        blogService.deleteBlog(id, UserContext.getUser());
        return ResponseEntity.noContent().build();
    }
}
