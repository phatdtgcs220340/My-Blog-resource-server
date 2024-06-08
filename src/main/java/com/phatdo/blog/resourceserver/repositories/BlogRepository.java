package com.phatdo.blog.resourceserver.repositories;

import com.phatdo.blog.resourceserver.models.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Blog, Long> {
}
