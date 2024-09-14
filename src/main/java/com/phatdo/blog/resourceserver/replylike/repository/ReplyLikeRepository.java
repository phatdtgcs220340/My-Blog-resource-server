package com.phatdo.blog.resourceserver.replylike.repository;

import com.phatdo.blog.resourceserver.replylike.model.ReplyLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyLikeRepository extends JpaRepository<ReplyLike, Long> {
}
