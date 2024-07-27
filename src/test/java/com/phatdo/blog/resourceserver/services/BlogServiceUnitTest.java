package com.phatdo.blog.resourceserver.services;

import com.phatdo.blog.resourceserver.dto.requests.UpdateBlogDTO;
import com.phatdo.blog.resourceserver.models.blogs.BlogType;
import com.phatdo.blog.resourceserver.exception.CustomException;
import com.phatdo.blog.resourceserver.models.blogs.Blog;
import com.phatdo.blog.resourceserver.models.users.User;
import com.phatdo.blog.resourceserver.repositories.BlogRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BlogServiceUnitTest {
    private final User user = new User("Do Tan Phat", "ddtphat2004@gmail.com");

    @Mock
    private BlogRepository blogRepository;

    @InjectMocks
    private BlogService blogService;

    // Pass
    @Test
    @WithMockUser(username = "phatdo", roles = {"ADMIN"})
    public void testDeleteBlog() throws CustomException {
        Blog blog = new Blog(BlogType.DAILY_BLOG, user);
        blog.setId(1);
        blog.setTitle("This is a test title");
        blog.setContent("This is a test content");

        when(blogRepository.findById(blog.getId()))
                .thenReturn(Optional.of(blog));

        blogService.deleteBlog(blog.getId(), user);
        verify(blogRepository).deleteById(1L);
    }

    // Pass
    @Test
    @WithMockUser(username = "phatdo")
    public void testDeleteBlogShouldThrowCustomExceptionBecauseBlogNotExist() {
        when(blogRepository.findById(2L))
                .thenReturn(Optional.empty()); // Return empty to simulate non-existing blog

        assertThrows(CustomException.class, () -> blogService.deleteBlog(2L, user));
        verify(blogRepository, never()).deleteById(anyLong());
    }

    // Pass
    @Test
    @WithMockUser(username = "phatdo", roles = { "ADMIN" })
    public void testUpdateBlogShouldSuccess() throws CustomException {
        Blog blog = new Blog(BlogType.DAILY_BLOG, user);
        blog.setId(1);
        blog.setTitle("This is a test title");
        blog.setContent("This is a test content");

        when(blogRepository.findById(blog.getId()))
                .thenReturn(Optional.of(blog));
        UpdateBlogDTO dto = new UpdateBlogDTO("title", "This is a new test content");

        blogService.updateBlog(blog.getId(), dto);
        verify(blogRepository).save(blog);
    }

    // Pass
    @Test
    @WithMockUser(username = "phatdo")
    public void testUpdateBlogThrownBlogNotFoundException() {
        Blog blog = new Blog(BlogType.DAILY_BLOG, user);
        blog.setId(1);
        blog.setTitle("This is a test title");
        blog.setContent("This is a test content");

        UpdateBlogDTO dto = new UpdateBlogDTO("title", "This is a new test content");
        assertThrows(CustomException.class, () -> blogService.updateBlog(blog.getId(), dto));
        verify(blogRepository, never()).save(blog);
    }
}
