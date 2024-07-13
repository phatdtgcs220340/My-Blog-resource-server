package com.phatdo.blog.resourceserver.models.images;

import com.phatdo.blog.resourceserver.models.blogs.Blog;
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
    private final String url;
    private final Timestamp createdAt = Timestamp.from(Instant.now());

    @ManyToOne
    @JoinColumn(nullable = false, name = "blog_id")
    private final Blog blog;
}
