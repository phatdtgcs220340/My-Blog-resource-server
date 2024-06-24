package com.phatdo.blog.resourceserver.controllers;

import com.phatdo.blog.resourceserver.authentication.UserContext;
import com.phatdo.blog.resourceserver.dto.responses.TypeDTO;
import com.phatdo.blog.resourceserver.mappers.DTOMapper;
import com.phatdo.blog.resourceserver.mappers.UserMapper;
import com.phatdo.blog.resourceserver.models.users.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/user", produces = "application/json")
public class UserController {
    private final DTOMapper<User> mapper = new UserMapper();

    @GetMapping
    public ResponseEntity<TypeDTO> getUser() {
        return ResponseEntity.ok(mapper.toDTO(UserContext.getUser()));
    }
}
