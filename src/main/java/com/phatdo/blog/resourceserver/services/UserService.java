package com.phatdo.blog.resourceserver.services;

import com.nimbusds.jwt.JWTClaimsSet;
import com.phatdo.blog.resourceserver.models.users.User;
import com.phatdo.blog.resourceserver.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Slf4j
@Service
public class UserService {
    private final UserRepository userRepository;
    private final JwtDecoderService jwtDecoderService;

    public UserService(UserRepository userRepository, JwtDecoderService jwtDecoderService) {
        this.userRepository = userRepository;
        this.jwtDecoderService = jwtDecoderService;
    }

    public User register(String token) throws ParseException {
        JWTClaimsSet claimsSet = jwtDecoderService.decode(token);
        String username = claimsSet.getSubject();
        String fullName = claimsSet.getStringClaim("fullName");
        String avatar = claimsSet.getStringClaim("picture");
        User user = new User(fullName, username);
        user.setAvatarUrl(avatar);
        return userRepository.save(user);
    }

    public User loadUserBySubject(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
