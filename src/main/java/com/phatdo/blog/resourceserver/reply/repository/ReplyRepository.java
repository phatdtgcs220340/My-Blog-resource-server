package com.phatdo.blog.resourceserver.reply.repository;

import com.phatdo.blog.resourceserver.reply.model.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findByBlogId(Long blogId);
}
