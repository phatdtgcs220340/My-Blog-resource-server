package com.phatdo.blog.resourceserver.repositories;

import com.phatdo.blog.resourceserver.models.bloglikes.BlogLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlogLikeRepository extends JpaRepository<BlogLike, Long> {
    boolean existsByUserIdAndBlogId(Long userId, Long blogId);
    Optional<BlogLike> findByUserIdAndBlogId(Long userId, Long blogId);
}
