package com.phatdo.blog.resourceserver.services;

import com.phatdo.blog.resourceserver.models.blogs.BlogType;
import com.phatdo.blog.resourceserver.exception.CustomException;
import com.phatdo.blog.resourceserver.models.blogs.Blog;
import com.phatdo.blog.resourceserver.models.replies.Reply;
import com.phatdo.blog.resourceserver.models.users.User;
import com.phatdo.blog.resourceserver.repositories.BlogRepository;
import com.phatdo.blog.resourceserver.repositories.ReplyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ReplyServiceUnitTest {
    private final User user = new User("Do Tan Phat", "ddtphat2004@gmail.com");
    @Mock
    private ReplyRepository replyRepository;
    @Mock
    private BlogRepository blogRepository;

    @InjectMocks
    private ReplyService replyService;

    @Test
    @WithMockUser(username = "phat")
    public void testSave() throws CustomException {
        Blog blog = new Blog(BlogType.DAILY_BLOG, user);
        blog.setTitle("Title");
        blog.setContent("Content");
        blog.setId(1L);
        when(blogRepository.findById(blog.getId()))
                .thenReturn(Optional.of(blog));
        replyService.save("a comment", user, blog.getId());
        verify(replyRepository).save(new Reply(user, blog));
    }
}
