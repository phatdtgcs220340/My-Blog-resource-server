package com.phatdo.blog.resourceserver.services;

import com.phatdo.blog.resourceserver.exception.CustomError;
import com.phatdo.blog.resourceserver.exception.CustomException;
import com.phatdo.blog.resourceserver.models.Blog;
import com.phatdo.blog.resourceserver.models.User;
import com.phatdo.blog.resourceserver.repositories.BlogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BlogService {
    private final BlogRepository blogRepository;

    public BlogService(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Blog saveBlog(String title, String content, User user) {
        Blog blog = new Blog(user);
        blog.setTitle(title);
        blog.setContent(content);
        user.getBlogs().add(blog);
        return blogRepository.save(blog);
    }

    public Blog getBlog(Long id) throws CustomException {
        return blogRepository.findById(id)
                .orElseThrow(() -> new CustomException(CustomError.BLOG_NOT_FOUND));
    }
}
