package com.phatdo.blog.resourceserver.user.controller;

import com.phatdo.blog.resourceserver.utils.authentication.UserContext;
import com.phatdo.blog.resourceserver.utils.commons.dto.TypeDTO;
import com.phatdo.blog.resourceserver.utils.mappers.DTOMapperE;
import com.phatdo.blog.resourceserver.utils.mappers.DTOMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.ok(mapperFactory.getMapper(DTOMapperE.USER).toDTO(UserContext.getUser()));
    }

}
