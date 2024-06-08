package com.phatdo.blog.resourceserver.configuration.security;

import com.phatdo.blog.resourceserver.configuration.jwt.JwtAuthenticationConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    @Value("${spring.security.oauth2.resource-server.jwt.jwk-set-uri}")
    private String keySetUri;
    private final JwtAuthenticationConverter converter;

    @Autowired
    public SecurityConfig(JwtAuthenticationConverter converter) {
        this.converter = converter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(c -> c
                        .requestMatchers("/api/admin/**","api/admin").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .oauth2ResourceServer(c -> c
                        .jwt(j -> j
                                .jwkSetUri(keySetUri)
                                .jwtAuthenticationConverter(converter)));
        return http.build();
    }
}
