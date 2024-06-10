package com.phatdo.blog.resourceserver.services;

import com.phatdo.blog.resourceserver.exception.CustomError;
import com.phatdo.blog.resourceserver.exception.CustomException;
import com.phatdo.blog.resourceserver.models.Blog;
import com.phatdo.blog.resourceserver.models.Reply;
import com.phatdo.blog.resourceserver.models.User;
import com.phatdo.blog.resourceserver.repositories.BlogRepository;
import com.phatdo.blog.resourceserver.repositories.ReplyRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReplyService {
    private final ReplyRepository replyRepository;
    private final BlogRepository blogRepository;

    public ReplyService(ReplyRepository replyRepository,
                        BlogRepository blogRepository) {
        this.replyRepository = replyRepository;
        this.blogRepository = blogRepository;
    }

    public Reply save(String content, User user, long blogId) throws CustomException {
        return blogRepository.findById(blogId)
                .map(blog -> {
                    Reply reply = new Reply(user, blog);
                    reply.setContent(content);
                    user.getReplies().add(reply);
                    blog.getReplies().add(reply);
                    return replyRepository.save(reply);
                }).orElseThrow(() -> new CustomException(CustomError.BLOG_NOT_FOUND));
    }

    public List<Reply> findByBlog(long id) throws CustomException {
        return blogRepository.findById(id)
                .map(blog -> {
                    return replyRepository.findByBlogId(blog.getId());
                }).orElseThrow(() -> new CustomException(CustomError.BLOG_NOT_FOUND));
    }
}
