package com.phatdo.blog.resourceserver.models;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "\"reply_like\"")
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@RequiredArgsConstructor
public class ReplyLike {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false, name = "like_id")
    private long id;

    private Timestamp likedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private final User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private final Reply reply;
}
