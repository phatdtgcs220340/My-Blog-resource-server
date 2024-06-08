package com.phatdo.blog.resourceserver.repositories;

import com.phatdo.blog.resourceserver.models.ReplyLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyLikeRepository extends JpaRepository<ReplyLike, Long> {
}
