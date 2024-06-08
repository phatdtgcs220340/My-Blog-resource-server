package com.phatdo.blog.resourceserver.models;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;
import java.util.Set;

@Data
@Entity
@Table(name = "\"reply\"")
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@RequiredArgsConstructor
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false, name = "reply_id")
    private long id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    private final User user;

    @ManyToOne
    @JoinColumn(nullable = false, name = "blog_id")
    private final Blog blog;

    @OneToMany(mappedBy = "reply",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private final Set<ReplyLike> likes;
}