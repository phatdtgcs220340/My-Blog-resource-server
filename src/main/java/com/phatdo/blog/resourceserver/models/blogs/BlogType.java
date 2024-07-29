package com.phatdo.blog.resourceserver.models.blogs;

import lombok.Getter;

@Getter
public enum BlogType {
    THANH_HUYEN("Thanh Huyen"),
    BOOK("Book"),
    LIFE_STYLE("Life style"),
    DAILY_BLOG("Daily blog"),
    SELF_TALK("Self talk"),;

    private final String name;
    BlogType(String name) {
        this.name = name;
    }
}
