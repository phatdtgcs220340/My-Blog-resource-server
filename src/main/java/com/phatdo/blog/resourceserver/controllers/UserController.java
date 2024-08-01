package com.phatdo.blog.resourceserver.controllers;

import com.phatdo.blog.resourceserver.authentication.UserContext;
import com.phatdo.blog.resourceserver.dto.responses.TypeDTO;
import com.phatdo.blog.resourceserver.mappers.DTOMapperE;
import com.phatdo.blog.resourceserver.mappers.DTOMapperFactory;
import com.phatdo.blog.resourceserver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping(path = "/api/v1/user", produces = "application/json")
public class UserController {
    private final DTOMapperFactory mapperFactory;
    private final UserService userService;

    @Autowired
    public UserController(DTOMapperFactory mapperFactory, UserService userService) {
        this.mapperFactory = mapperFactory;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<TypeDTO> getUser() {
        return ResponseEntity.ok(mapperFactory.getMapper(DTOMapperE.USER).toDTO(UserContext.getUser()));
    }

    @PostMapping
    public ResponseEntity<TypeDTO> createUser(@RequestHeader("Authorization") String token) throws ParseException {
        userService.register(token.replace("Bearer ", ""));
        return ResponseEntity.ok().build();
    }
}
