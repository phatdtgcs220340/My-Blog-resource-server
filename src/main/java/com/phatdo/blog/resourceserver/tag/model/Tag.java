package com.phatdo.blog.resourceserver.tag.model;

import com.phatdo.blog.resourceserver.blog.model.Blog;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "\"tag\"")
@NoArgsConstructor(access = AccessLevel.PUBLIC, force = true)
@RequiredArgsConstructor
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tag_id")
    private long id;
    private final String name;

    @ManyToMany
    private Set<Blog> blogs = new HashSet<>();
}
