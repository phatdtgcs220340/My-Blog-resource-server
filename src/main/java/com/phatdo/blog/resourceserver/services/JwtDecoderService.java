package com.phatdo.blog.resourceserver.services;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
public class JwtDecoderService {
    private final JwtDecoder jwtDecoder;

    public JwtDecoderService(JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    public JWTClaimsSet decode(String token) throws ParseException {
        Jwt jwt = jwtDecoder.decode(token);
        return SignedJWT.parse(jwt.getTokenValue()).getJWTClaimsSet();
    }
}
