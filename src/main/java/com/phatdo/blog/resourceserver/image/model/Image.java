package com.phatdo.blog.resourceserver.image.model;

import com.phatdo.blog.resourceserver.blog.model.Blog;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "\"image\"")
@Data
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false, name = "image_id")
    private long id;

    private final String originalImageUrl;
    private String resizedImageUrl;

    private final Timestamp createdAt = Timestamp.from(Instant.now());

    private String description;

    @ManyToOne
    @JoinColumn(nullable = true, name = "blog_id")
    private Blog blog;
}
