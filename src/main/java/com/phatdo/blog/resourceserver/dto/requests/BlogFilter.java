package com.phatdo.blog.resourceserver.dto.requests;

import org.springframework.data.domain.Sort;

import java.util.List;

public record BlogFilter(
        String name,
        List<String> tags,
        Sort.Direction direction
) {
}
