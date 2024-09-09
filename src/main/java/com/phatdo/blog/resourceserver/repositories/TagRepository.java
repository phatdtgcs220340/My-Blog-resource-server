package com.phatdo.blog.resourceserver.repositories;

import com.phatdo.blog.resourceserver.models.tags.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {
    @Query(value = "SELECT * FROM tag t WHERE t.name IN :names", nativeQuery = true)
    List<Tag> findByListName(@Param("names") Collection<String> names);
}
