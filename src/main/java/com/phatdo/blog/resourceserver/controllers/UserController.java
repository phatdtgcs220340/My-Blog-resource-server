package com.phatdo.blog.resourceserver.controllers;

import com.phatdo.blog.resourceserver.authentication.UserContext;
import com.phatdo.blog.resourceserver.classification.TypeDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/user", produces = "application/json")
public class UserController {
    @GetMapping
    public ResponseEntity<TypeDTO> getUser() {
        return ResponseEntity.ok(UserContext.getUser().toDTO());
    }
}
