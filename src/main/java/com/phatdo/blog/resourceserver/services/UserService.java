package com.phatdo.blog.resourceserver.services;

import com.phatdo.blog.resourceserver.models.users.User;
import com.phatdo.blog.resourceserver.repositories.UserRepository;
import com.phatdo.blog.resourceserver.models.users.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Slf4j
@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /***
     * Function that save a user or find an existed user in
     * the database post OAuth2 log in
     * @param fullName : get from fullName jwt claim key
     * @param username : get from jwt subject
     * @param authorities : set authorities from subject
     * @return User
     */
    public User processOAuth2Login(String fullName, String username, Collection<GrantedAuthority> authorities) {
        return userRepository
                .findByUsername(username)
                .map(user -> {
                    log.info("User found with username: {}", username);
                    return user;
                })
                .orElseGet(() -> {
                    log.info("Attempt to save user with username: {}", username);
                    User user = new User(fullName, username);
                    user.getRoles().addAll(authorities
                            .stream()
                            .map((authority) -> UserRole.valueOf(authority.getAuthority()))
                            .toList());
                    return userRepository.save(user);
                });
    }
}
