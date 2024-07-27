package com.phatdo.blog.resourceserver.services;

import com.phatdo.blog.resourceserver.dto.requests.CreateBlogDTO;
import com.phatdo.blog.resourceserver.dto.requests.UpdateBlogDTO;
import com.phatdo.blog.resourceserver.exception.CustomError;
import com.phatdo.blog.resourceserver.exception.CustomException;
import com.phatdo.blog.resourceserver.models.blogs.Blog;
import com.phatdo.blog.resourceserver.models.users.User;
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
     * A function that create a blog with admin privilege
     * @param form : request create blog
     * @param user : user whom pass by UserContext
     * @return A Blog if successfully save
     */
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Blog saveBlog(CreateBlogDTO form, User user) {
        Blog blog = new Blog(form.type(), user);
        blog.setTitle(form.title());
        blog.setContent(form.content());
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
     * Find all blogs with starting page and size
     * @param pageable : pass by page and size request param
     * @return : A page with Page of posts
     */
    public Page<Blog> getAllBlogs(Pageable pageable) {
        return blogRepository.findAllByOrderByCreatedDateDesc(pageable);
    }

    /**
     * Update a blog
     * @param id : id of a blog
     * @param dto : update dto
     * @return : An updated blog
     * @throws CustomException : will be thrown if the blog isn't existed
     */
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Blog updateBlog(Long id,UpdateBlogDTO dto) throws CustomException {
        Optional<Blog> optBlog = blogRepository.findById(id);
        if (optBlog.isPresent()) {
            Blog blog = optBlog.get();
            if (!dto.title().isEmpty())
                blog.setTitle(dto.title());
            if (!dto.content().isEmpty())
                blog.setContent(dto.content());
            return blogRepository.save(blog);
        }
        else
            throw new CustomException(CustomError.BLOG_NOT_FOUND);

    }

    /**
     * Delete a blog and remove it from user's blog list
     * @param id : expected blog to be deleted
     * @throws CustomException : will be thrown if it couldn't find blog
     */
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void deleteBlog(Long id, User user) throws CustomException {
        Optional<Blog> optBlog = blogRepository.findById(id);
        if (optBlog.isPresent()) {
            user.getBlogs().remove(optBlog.get());
            blogRepository.deleteById(id);
        }
        else
            throw new CustomException(CustomError.BLOG_NOT_FOUND);
    }
}
