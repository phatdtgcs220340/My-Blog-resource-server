package com.phatdo.blog.resourceserver.classification;

public enum BlogType {
    THANH_HUYEN("Em và tôi"),
    TU_SU("Chuyện ");
    private final String name;
    BlogType(String name) {
        this.name = name;
    }
}
