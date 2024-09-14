package com.phatdo.blog.resourceserver.blog.request;

import org.springframework.data.domain.Sort;

import java.util.List;

public record BlogFilter(
        String name,
        List<String> tags,
        Sort.Direction direction
) {
}
