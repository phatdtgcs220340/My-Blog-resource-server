package com.phatdo.blog.resourceserver.models;

import com.phatdo.blog.resourceserver.dto.responses.ReplyDTO;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
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
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private final Set<ReplyLike> likes = new HashSet<>();

    public ReplyDTO toDTO() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy - HH:mm a");
        String modifiedDateStr = updatedAt.toLocalDateTime().format(formatter);
        return new ReplyDTO(
                id,
                content,
                modifiedDateStr,
                user.getId(),
                user.getFullName(),
                likes.size()
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id); // Or any other unique identifier for User
    }
}
