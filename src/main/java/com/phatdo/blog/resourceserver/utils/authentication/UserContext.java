package com.phatdo.blog.resourceserver.utils.authentication;

import com.phatdo.blog.resourceserver.user.model.User;

public class UserContext {
    private static final ThreadLocal<User> userHolder = new ThreadLocal<>();

    public static void setUser(User user) {
        userHolder.set(user);
    }

    public static User getUser() {
        return userHolder.get();
    }

    public static void clear() {
        userHolder.remove();
    }
}
