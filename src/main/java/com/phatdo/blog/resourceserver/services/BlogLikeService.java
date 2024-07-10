//package com.phatdo.blog.resourceserver.services;
//
//import com.phatdo.blog.resourceserver.exception.CustomError;
//import com.phatdo.blog.resourceserver.exception.CustomException;
//import com.phatdo.blog.resourceserver.models.bloglikes.BlogLike;
//import com.phatdo.blog.resourceserver.models.blogs.Blog;
//import com.phatdo.blog.resourceserver.models.users.User;
//import com.phatdo.blog.resourceserver.repositories.BlogLikeRepository;
//import com.phatdo.blog.resourceserver.repositories.BlogRepository;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//public class BlogLikeService {
//    private final BlogLikeRepository blogLikeRepository;
//    private final BlogRepository blogRepository;
//
//    public BlogLikeService(BlogLikeRepository blogLikeRepository,
//                           BlogRepository blogRepository) {
//        this.blogLikeRepository = blogLikeRepository;
//        this.blogRepository = blogRepository;
//    }
//
//    public BlogLike doLike(Long blogId, User user) throws CustomException {
//        return blogRepository.findById(blogId)
//                .map(blog -> {
//                    BlogLike blogLike = new BlogLike(user, blog);
//                    blog.getLikes().add(blogLike);
//                    user.getBlogLikes().add(blogLike);
//                    return blogLikeRepository.save(blogLike);
//                }).orElseThrow(() -> new CustomException(CustomError.BLOG_NOT_FOUND));
//    }
//
//    public void unlike(Long id) throws CustomException {
//        Optional<Blog> optBlog = blogRepository.findById(blogId);
//        if (optBlog.isPresent()) {
//            Blog blog = optBlog.get();
//            blog.getLikes().remove(blog);
//            blog.getLikes().remove(blog);
//        }
//    }
//}
