package com.phatdo.blog.resourceserver.models;

import com.phatdo.blog.resourceserver.classification.BlogType;
import com.phatdo.blog.resourceserver.dto.responses.OneBlogDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
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

        @NotNull
        private final BlogType blogType;

        private final Timestamp createdDate = Timestamp.from(Instant.now());

        private Timestamp modifiedDate = Timestamp.from(Instant.now());

        @ManyToOne
        @JoinColumn(name = "user_id")
        private final User user;

        @OneToMany(mappedBy = "blog", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
        private final Set<BlogLike> likes = new HashSet<>();

        @OneToMany(mappedBy = "blog", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
        private final Set<Reply> replies = new HashSet<>();

        public OneBlogDTO toDTO() {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy - HH:mm a");
                String modifiedDateStr = modifiedDate.toLocalDateTime().format(formatter);
                return new OneBlogDTO(
                                id,
                                title,
                                content,
                                modifiedDateStr,
                                blogType.toString(),
                                user.getId(),
                                user.getFullName(),
                                likes.size(),
                                replies.size());
        }
}
