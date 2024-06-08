package com.phatdo.blog.resourceserver.models;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "\"blog_like\"")
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@RequiredArgsConstructor
public class BlogLike {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false, name = "like_id")
    private long id;

    private Timestamp likedAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private final User user;

    @ManyToOne
    @JoinColumn(name = "blog_id", nullable = false)
    private final Blog blog;
}
