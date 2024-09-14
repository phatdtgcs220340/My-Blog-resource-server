package com.phatdo.blog.resourceserver.bloglike.service;

import com.phatdo.blog.resourceserver.utils.commons.exception.CustomError;
import com.phatdo.blog.resourceserver.utils.commons.exception.CustomException;
import com.phatdo.blog.resourceserver.bloglike.model.BlogLike;
import com.phatdo.blog.resourceserver.blog.model.Blog;
import com.phatdo.blog.resourceserver.user.model.User;
import com.phatdo.blog.resourceserver.bloglike.repository.BlogLikeRepository;
import com.phatdo.blog.resourceserver.blog.repository.BlogRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BlogLikeService {
    private final BlogLikeRepository blogLikeRepository;
    private final BlogRepository blogRepository;

    public BlogLikeService(BlogLikeRepository blogLikeRepository,
                           BlogRepository blogRepository) {
        this.blogLikeRepository = blogLikeRepository;
        this.blogRepository = blogRepository;
    }

    public void like(Long blogId, User user) throws CustomException {
        Optional<Blog> optBlog = blogRepository.findById(blogId);
        if (optBlog.isPresent()) {
            Blog blogEntity = optBlog.get();
            if (blogLikeRepository.existsByUserIdAndBlogId(user.getId(), blogId))
                throw new CustomException(CustomError.DUPLICATED_LIKE);
            else {
                BlogLike like = blogLikeRepository.save(new BlogLike(user, blogEntity));
                blogEntity.getLikes().add(like);
            }
        }
        else throw new CustomException(CustomError.BLOG_NOT_FOUND);
    }

    public void unlike(Long blogId, User user) throws CustomException {
        Optional<Blog> optBlog = blogRepository.findById(blogId);
        if (optBlog.isPresent()) {
            blogLikeRepository.findByUserIdAndBlogId(user.getId(), blogId)
                    .map(e -> {
                        blogLikeRepository.delete(e);
                        return new BlogLike();
                    }).orElseThrow(() -> new CustomException(CustomError.LIKE_NOT_FOUND));
        }
        else throw new CustomException(CustomError.BLOG_NOT_FOUND);
    }
}
