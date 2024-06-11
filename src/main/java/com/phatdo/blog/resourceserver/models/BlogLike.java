package com.phatdo.blog.resourceserver.models;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;

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

    private final Timestamp likedAt = Timestamp.from(Instant.now());

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private final User user;

    @ManyToOne
    @JoinColumn(name = "blog_id", nullable = false)
    private final Blog blog;

    @Override
    public int hashCode() {
        return Objects.hash(id); // Or any other unique identifier for User
    }
}
