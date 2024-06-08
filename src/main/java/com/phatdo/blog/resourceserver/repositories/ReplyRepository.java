package com.phatdo.blog.resourceserver.repositories;

import com.phatdo.blog.resourceserver.models.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
}
