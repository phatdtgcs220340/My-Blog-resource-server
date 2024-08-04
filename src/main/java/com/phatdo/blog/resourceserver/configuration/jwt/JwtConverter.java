package com.phatdo.blog.resourceserver.configuration.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class JwtConverter implements Converter<Jwt, JwtAuthenticationToken> {

    @Override
    public JwtAuthenticationToken convert(Jwt source) {
        log.info("Subject {}", source.getSubject());
        List<GrantedAuthority> convertedAuthorities = new ArrayList<>();
        source.getClaimAsStringList("roles").forEach(claim ->
                convertedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + claim)));
        return new JwtAuthenticationToken(source, convertedAuthorities);
    }
}
