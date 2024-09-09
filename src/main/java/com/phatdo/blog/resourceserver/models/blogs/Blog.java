package com.phatdo.blog.resourceserver.models.blogs;

import com.phatdo.blog.resourceserver.models.bloglikes.BlogLike;
import com.phatdo.blog.resourceserver.models.images.Image;
import com.phatdo.blog.resourceserver.models.replies.Reply;
import com.phatdo.blog.resourceserver.models.tags.Tag;
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

        @ManyToMany
        @JoinTable(
                name = "blog_tag",
                joinColumns = @JoinColumn(name = "blog_id"),
                inverseJoinColumns = @JoinColumn(name = "tag_id")
        )
        private final Set<Tag> tags = new HashSet<>();

        private final Timestamp createdDate = Timestamp.from(Instant.now());

        private Timestamp modifiedDate = Timestamp.from(Instant.now());

        @ManyToOne
        @JoinColumn(name = "user_id")
        private final User user;

        @OneToMany(mappedBy = "blog", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
        private final Set<BlogLike> likes = new HashSet<>();

        @OneToMany(mappedBy = "blog", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
        private final Set<Reply> replies = new HashSet<>();

        @OneToMany(mappedBy = "blog", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
        private final Set<Image> images = new HashSet<>();

        @Override
        public int hashCode() {
                return Objects.hash(id); // Or any other unique identifier for User
        }

        @Override
        public String toString() {
                return String.format("{ title: %s, content: %s, tags: %s }", title, content, tags.toString());
        }
}
