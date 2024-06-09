package com.phatdo.blog.resourceserver.services;

import com.phatdo.blog.resourceserver.classification.BlogType;
import com.phatdo.blog.resourceserver.exception.CustomError;
import com.phatdo.blog.resourceserver.exception.CustomException;
import com.phatdo.blog.resourceserver.models.Blog;
import com.phatdo.blog.resourceserver.models.User;
import com.phatdo.blog.resourceserver.repositories.BlogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Slf4j
@Service
public class BlogService {
    private final BlogRepository blogRepository;

    public BlogService(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    /**
     * @brief A function that create a blog with admin privilege
     * @param title : title of the blog
     * @param content : content of the blog
     * @param user : user whom pass by UserContext
     * @return A Blog if successfully save
     */
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Blog saveBlog(String title, String content, BlogType type, User user) {
        Blog blog = new Blog(type, user);
        blog.setTitle(title);
        blog.setContent(content);
        user.getBlogs().add(blog);
        return blogRepository.save(blog);
    }

    /**
     * A function find a post with a provided id
     * @param id : post id
     * @return : A Blog
     * @throws CustomException which has 404 error
     */
    public Blog getBlog(Long id) throws CustomException {
        return blogRepository.findById(id)
                .orElseThrow(() -> new CustomException(CustomError.BLOG_NOT_FOUND));
    }

    /**
     * @brief Find all blogs with starting page and size
     * @param pageable : pass by page and size request param
     * @return : A page with Page of posts
     */
    public Page<Blog> getAllBlogs(Pageable pageable) {
        return blogRepository.findAllByOrderByCreatedDateDesc(pageable);
    }
}
