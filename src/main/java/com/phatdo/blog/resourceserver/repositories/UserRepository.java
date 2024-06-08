package com.phatdo.blog.resourceserver.repositories;

import com.phatdo.blog.resourceserver.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
