package com.phatdo.blog.resourceserver.models.replylikes;

import com.phatdo.blog.resourceserver.models.replies.Reply;
import com.phatdo.blog.resourceserver.models.users.User;
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
@Table(name = "\"reply_like\"")
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@RequiredArgsConstructor
public class ReplyLike {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false, name = "like_id")
    private long id;

    private final Timestamp likedAt = Timestamp.from(Instant.now());

    @ManyToOne(fetch = FetchType.LAZY)
    private final User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private final Reply reply;

    @Override
    public int hashCode() {
        return Objects.hash(id); // Or any other unique identifier for User
    }
}
