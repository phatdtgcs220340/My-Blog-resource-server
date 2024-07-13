package com.phatdo.blog.resourceserver.repositories;

import com.phatdo.blog.resourceserver.models.images.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
