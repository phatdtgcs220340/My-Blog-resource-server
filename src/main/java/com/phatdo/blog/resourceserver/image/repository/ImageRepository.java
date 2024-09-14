package com.phatdo.blog.resourceserver.image.repository;

import com.phatdo.blog.resourceserver.image.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
