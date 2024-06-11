package com.phatdo.blog.resourceserver.repositories;

import com.phatdo.blog.resourceserver.models.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Blog, Long> {
    Page<Blog> findAllByOrderByCreatedDateDesc(Pageable pageable);
}
