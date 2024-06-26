package com.phatdo.blog.resourceserver.models.replies;

import com.phatdo.blog.resourceserver.models.replylikes.ReplyLike;
import com.phatdo.blog.resourceserver.models.blogs.Blog;
import com.phatdo.blog.resourceserver.models.users.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
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

    private final Timestamp createdAt = Timestamp.from(Instant.now());

    private Timestamp updatedAt = Timestamp.from(Instant.now());

    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    private final User user;

    @ManyToOne
    @JoinColumn(nullable = false, name = "blog_id")
    private final Blog blog;

    @OneToMany(mappedBy = "reply",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private final Set<ReplyLike> likes = new HashSet<>();

    @Override
    public int hashCode() {
        return Objects.hash(id); // Or any other unique identifier for User
    }

    @Override
    public String toString() {
        return String.format("Content: %s%nBlogId: %d", content, blog.getId());
    }
}
