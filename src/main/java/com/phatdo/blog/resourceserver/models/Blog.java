package com.phatdo.blog.resourceserver.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "\"blog\"")
@NoArgsConstructor(access = AccessLevel.PUBLIC, force = true)
@RequiredArgsConstructor
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false, name = "blog_id")
    private long id;

    @NotNull
    private String title;

    @NotNull
    @Column(columnDefinition = "TEXT")
    private String content;

    private final Timestamp createdDate = Timestamp.from(Instant.now());

    private Timestamp modifiedDate = Timestamp.from(Instant.now());

    @ManyToOne
    @JoinColumn(name = "user_id")
    private final User user;

    @OneToMany(mappedBy = "blog",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private final Set<BlogLike> likes = new HashSet<>();

    @OneToMany(mappedBy = "blog",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private final Set<Reply> replies = new HashSet<>();
}
