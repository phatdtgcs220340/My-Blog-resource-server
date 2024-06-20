package com.phatdo.blog.resourceserver.models;

import com.phatdo.blog.resourceserver.classification.BlogType;
import com.phatdo.blog.resourceserver.dto.responses.FullBlogDTO;
import com.phatdo.blog.resourceserver.dto.responses.PartialBlogDTO;
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
@Table(name = "\"blog\"")
@NoArgsConstructor(access = AccessLevel.PUBLIC, force = true)
@RequiredArgsConstructor
public class Blog {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(unique = true, nullable = false, name = "blog_id")
        private long id;

        private String title;

        @Column(columnDefinition = "TEXT")
        private String content;

        private final BlogType blogType;

        private final Timestamp createdDate = Timestamp.from(Instant.now());

        private Timestamp modifiedDate = Timestamp.from(Instant.now());

        @ManyToOne
        @JoinColumn(name = "user_id")
        private final User user;

        @OneToMany(mappedBy = "blog", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
        private final Set<BlogLike> likes = new HashSet<>();

        @OneToMany(mappedBy = "blog", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
        private final Set<Reply> replies = new HashSet<>();

        public FullBlogDTO toDTO() {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy - HH:mm a");
                String modifiedDateStr = modifiedDate.toLocalDateTime().format(formatter);
                return new FullBlogDTO(
                                id,
                                title,
                                content,
                                blogType.toString(),
                                modifiedDateStr,
                                user.getId(),
                                user.getFullName(),
                                likes.size(),
                                replies.size());
        }

        public PartialBlogDTO toPartialDTO() {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy - HH:mm a");
                String modifiedDateStr = modifiedDate.toLocalDateTime().format(formatter);
                return new PartialBlogDTO(
                        id,
                        title,
                        blogType.toString(),
                        modifiedDateStr,
                        user.getId(),
                        user.getFullName(),
                        likes.size(),
                        replies.size());
        }

        @Override
        public int hashCode() {
                return Objects.hash(id); // Or any other unique identifier for User
        }

        @Override
        public String toString() {
                return String.format("Title: %s%nContent: %s%nType: %s", title, content, blogType);
        }
}
