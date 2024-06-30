package com.phatdo.blog.resourceserver.controllers;

import com.phatdo.blog.resourceserver.authentication.UserContext;
import com.phatdo.blog.resourceserver.dto.responses.TypeDTO;
import com.phatdo.blog.resourceserver.mappers.DTOMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/user", produces = "application/json")
public class UserController {
    private final DTOMapperFactory mapperFactory;

    @Autowired
    public UserController(DTOMapperFactory mapperFactory) {
        this.mapperFactory = mapperFactory;
    }

    @GetMapping
    public ResponseEntity<TypeDTO> getUser() {
        return ResponseEntity.ok(mapperFactory.getMapper("user").toDTO(UserContext.getUser()));
    }
}
