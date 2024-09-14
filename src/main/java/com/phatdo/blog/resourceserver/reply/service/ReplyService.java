package com.phatdo.blog.resourceserver.reply.service;

import com.phatdo.blog.resourceserver.utils.commons.exception.CustomError;
import com.phatdo.blog.resourceserver.utils.commons.exception.CustomException;
import com.phatdo.blog.resourceserver.reply.model.Reply;
import com.phatdo.blog.resourceserver.user.model.User;
import com.phatdo.blog.resourceserver.user.model.UserRole;
import com.phatdo.blog.resourceserver.blog.repository.BlogRepository;
import com.phatdo.blog.resourceserver.reply.repository.ReplyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Reply service
 * @author phatdo 29/08/2024
 */
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
                })
                .orElseThrow(() -> new CustomException(CustomError.BLOG_NOT_FOUND));
    }

    public List<Reply> findByBlog(long id) throws CustomException {
        return blogRepository.findById(id)
                .map(blog -> replyRepository.findByBlogId(blog.getId())).orElseThrow(() -> new CustomException(CustomError.BLOG_NOT_FOUND));
    }

    public Reply updateReply(long id, String content, User user) throws CustomException {
        Optional<Reply> optReply = replyRepository.findById(id);
        if (optReply.isPresent()) {
            Reply reply = optReply.get();
            if (!reply.getUser().getUsername().equals(user.getUsername()))
                throw new CustomException(CustomError.ACCESS_DENIED);
            reply.setContent(content);
            return replyRepository.save(reply);
        }
        else
            throw new CustomException(CustomError.REPLY_NOT_FOUND);
    }

    public void deleteReply(long id, User user) throws CustomException {
        Optional<Reply> optReply = replyRepository.findById(id);
        if (optReply.isPresent()) {
            Reply reply = optReply.get();
            if (reply.getUser().getUsername().equals(user.getUsername()) || user.getRoles().contains(UserRole.ADMIN)) {
                reply.getBlog().getReplies().remove(reply);
                reply.getUser().getReplies().remove(reply);
                replyRepository.delete(reply);
            }
            else throw new CustomException(CustomError.ACCESS_DENIED);
        }
        else throw new CustomException(CustomError.REPLY_NOT_FOUND);
    }
}
