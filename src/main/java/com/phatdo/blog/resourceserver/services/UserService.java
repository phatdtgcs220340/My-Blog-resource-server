package com.phatdo.blog.resourceserver.services;

import com.nimbusds.jwt.JWTClaimsSet;
import com.phatdo.blog.resourceserver.exception.CustomError;
import com.phatdo.blog.resourceserver.exception.CustomException;
import com.phatdo.blog.resourceserver.models.users.User;
import com.phatdo.blog.resourceserver.models.users.UserRole;
import com.phatdo.blog.resourceserver.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {
    private final UserRepository userRepository;
    private final RedisTemplate<String, User> redisTemplate;
    private final JwtDecoderService jwtDecoderService;

    public UserService(UserRepository userRepository, RedisTemplate<String, User> redisTemplate, JwtDecoderService jwtDecoderService) {
        this.userRepository = userRepository;
        this.jwtDecoderService = jwtDecoderService;
        this.redisTemplate = redisTemplate;
    }

    public void register(String token) throws ParseException, CustomException {
        JWTClaimsSet claimsSet = jwtDecoderService.decode(token);
        String username = claimsSet.getSubject();
        String fullName = claimsSet.getStringClaim("name");
        String avatar = claimsSet.getStringClaim("picture");
        User user = new User(fullName, username);
        user.setAvatarUrl(avatar);
        user.getRoles().addAll(claimsSet.getStringListClaim("roles")
                .stream()
                .map(UserRole::valueOf)
                .collect(Collectors.toSet()));
        if (userRepository.findByUsername(username).isPresent())
            throw new CustomException(CustomError.USER_DUPLICATE);
        userRepository.save(user);
    }

    public User loadUserBySubject(String username) {
        String USER_CACHE_PREFIX = "user: ";
        String key = USER_CACHE_PREFIX + username;
        User user = redisTemplate.opsForValue().get(key);
        if (user != null) {
            log.info("Fetch user from cache: {}", user);
            return user;
        }
        else
            return userRepository.findByUsername(username).map(u -> {
                log.info("Save user to cache... {}", u);
                redisTemplate.opsForValue().set(key, u, 15, TimeUnit.MINUTES);
                return u;
            }).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }
}
