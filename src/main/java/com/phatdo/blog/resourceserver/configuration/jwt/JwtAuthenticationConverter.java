package com.phatdo.blog.resourceserver.configuration.jwt;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JwtAuthenticationConverter
        implements Converter<Jwt, CustomAuthentication> {
    @Override
    public CustomAuthentication convert(Jwt source) {
        String username = source.getSubject();
        List<GrantedAuthority> authorities;
        if (username.equals("ddtphat2004@gmail.com"))
            authorities = List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
        else authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
        return new CustomAuthentication(source, authorities);
    }
}
