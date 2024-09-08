package com.phatdo.blog.resourceserver.repositories;

import com.phatdo.blog.resourceserver.models.blogs.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Long> {
    Page<Blog> findAllByOrderByCreatedDateDesc(Pageable pageable);
    List<Blog> findByTitleContaining(String title, Pageable pageable);
}
