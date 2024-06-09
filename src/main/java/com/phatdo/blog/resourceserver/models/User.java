package com.phatdo.blog.resourceserver.models;

import com.phatdo.blog.resourceserver.classification.UserRole;
import com.phatdo.blog.resourceserver.dto.responses.UserDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/***
 * Class represent a user model, should be used as a publisher or a comment owner
 */
@Data
@Entity
@Table(name = "\"user\"")
@NoArgsConstructor(access = AccessLevel.PUBLIC, force = true)
@RequiredArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false, name = "user_id")
    private long id;

    @NotNull
    private final String fullName;

    @NotNull
    @Email
    private final String username;

    private final List<UserRole> roles = new ArrayList<>();

    private final Timestamp participatedDate = Timestamp.from(Instant.now());

    @OneToMany(mappedBy = "user",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private final Set<Blog> blogs = new HashSet<>();

    @OneToMany(mappedBy = "user",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private final Set<Reply> replies = new HashSet<>();

    @OneToMany(mappedBy = "user",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private final Set<Reply> blogLikes = new HashSet<>();

    @OneToMany(mappedBy = "user",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private final Set<Reply> replyLikes = new HashSet<>();

    public UserDTO toDTO() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy - HH:mm a");
        String modifiedDateStr = participatedDate.toLocalDateTime().format(formatter);
        return new UserDTO(
                id,
                fullName,
                roles
                        .stream()
                        .map(Enum::toString)
                        .toList(),
                modifiedDateStr);
    }
}
