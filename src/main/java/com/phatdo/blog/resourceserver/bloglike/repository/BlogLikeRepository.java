package com.phatdo.blog.resourceserver.bloglike.repository;

import com.phatdo.blog.resourceserver.bloglike.model.BlogLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlogLikeRepository extends JpaRepository<BlogLike, Long> {
    boolean existsByUserIdAndBlogId(Long userId, Long blogId);
    Optional<BlogLike> findByUserIdAndBlogId(Long userId, Long blogId);
}
