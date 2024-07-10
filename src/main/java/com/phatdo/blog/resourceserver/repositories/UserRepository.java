package com.phatdo.blog.resourceserver.repositories;

import com.phatdo.blog.resourceserver.models.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
