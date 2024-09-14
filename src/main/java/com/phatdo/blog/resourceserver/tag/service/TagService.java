package com.phatdo.blog.resourceserver.tag.service;

import com.phatdo.blog.resourceserver.tag.model.Tag;
import com.phatdo.blog.resourceserver.tag.repository.TagRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class TagService {
    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public Page<Tag> findBySearchName(String name, Pageable pageable) {
        return tagRepository.findByNameContaining(name, pageable);
    }

    public Page<Tag> findAll(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }
}
