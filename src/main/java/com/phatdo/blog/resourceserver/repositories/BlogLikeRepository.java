package com.phatdo.blog.resourceserver.repositories;

import com.phatdo.blog.resourceserver.models.BlogLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogLikeRepository extends JpaRepository<BlogLike, Long> {
}
