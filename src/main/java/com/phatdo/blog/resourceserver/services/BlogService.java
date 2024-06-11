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

import java.util.Optional;

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
    // chua handle duoc form thieu data
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Blog saveBlog(String title, String content, BlogType type, User user) {
        Blog blog = new Blog(type, user);
        blog.setTitle(title);
        blog.setContent(content);
        user.getBlogs().add(blog);
        return blogRepository.save(blog);
    }

    /**
     * @brief A function find a post with a provided id
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

    /**
     * @brief Update a blog
     * @param id : id of a blog
     * @param title : new title
     * @param content : new content
     * @return : An updated blog
     * @throws CustomException : will be thrown if the blog isn't existed
     */
    // chua handle duoc thieu data
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Blog updateBlog(Long id, String title, String content) throws CustomException {
           return blogRepository.findById(id)
                   .map(blog -> {
                       if (!title.isEmpty())
                           blog.setTitle(title);
                       if (!content.isEmpty())
                           blog.setContent(content);
                       return blogRepository.save(blog);
           })
                   .orElseThrow(() -> new CustomException(CustomError.BLOG_NOT_FOUND));
    }
    /**
     * @brief Delete a blog and remove it from user's blog list
     * @param id : expected blog to be deleted
     * @throws CustomException : will be throw if couldn't find blog
     */
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void deleteBlog(Long id) throws CustomException {
        Optional<Blog> optBlog = blogRepository.findById(id);
        if (optBlog.isPresent()) {
            User user = optBlog.get().getUser();
            user.getBlogs().remove(optBlog.get());
            blogRepository.deleteById(id);
        }
        else
            throw new CustomException(CustomError.BLOG_NOT_FOUND);
    }
}
