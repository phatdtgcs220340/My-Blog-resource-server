package com.phatdo.blog.resourceserver.user.controller;

import com.phatdo.blog.resourceserver.utils.commons.dto.TypeDTO;
import com.phatdo.blog.resourceserver.utils.commons.exception.CustomException;
import com.phatdo.blog.resourceserver.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

import static com.phatdo.blog.resourceserver.utils.commons.path.CommonApi.API_REGISTER;

@Slf4j
@RestController
@RequestMapping(API_REGISTER)
public class RegisterController {
    private final UserService userService;

    @Autowired
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<TypeDTO> register(@RequestHeader("Id-Token") String token) throws ParseException, CustomException {
        log.info("Token found: {}", token);
        userService.register(token);
        return ResponseEntity.ok().build();
    }
}
