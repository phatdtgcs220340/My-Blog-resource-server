package com.phatdo.blog.resourceserver.authentication;

import com.phatdo.blog.resourceserver.models.User;
import com.phatdo.blog.resourceserver.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class CustomFilter extends OncePerRequestFilter {
    private final UserService userService;

    @Autowired
    public CustomFilter(UserService userService) {
        this.userService = userService;
    }

    /***
     * An event listener after log in with jwt token
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JwtAuthenticationToken jwtAuthentication) {
            String fullName = jwtAuthentication.getTokenAttributes().get("fullName").toString();
            User user = userService.processOAuth2Login(fullName, authentication.getName(), jwtAuthentication.getAuthorities());
            UserContext.setUser(user);
        }
        try {
            filterChain.doFilter(request, response);
        } finally {
            UserContext.clear();
        }
    }
}
