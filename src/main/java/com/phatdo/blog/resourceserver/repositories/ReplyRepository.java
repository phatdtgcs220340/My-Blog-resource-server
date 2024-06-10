package com.phatdo.blog.resourceserver.repositories;

import com.phatdo.blog.resourceserver.models.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findByBlogId(Long blogId);
}
