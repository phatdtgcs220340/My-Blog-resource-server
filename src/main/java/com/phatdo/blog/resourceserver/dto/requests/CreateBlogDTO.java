package com.phatdo.blog.resourceserver.dto.requests;

import com.phatdo.blog.resourceserver.classification.BlogType;

public record CreateBlogDTO(String title, String content, BlogType type) {
}
