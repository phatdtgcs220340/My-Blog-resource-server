package com.phatdo.blog.resourceserver.configuration.security;

import com.phatdo.blog.resourceserver.configuration.jwt.JwtAuthenticationConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

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
                        .requestMatchers(HttpMethod.GET,"/api/v1/user").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v1/blog", "/api/v1/blog/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/reply").permitAll()
                        .anyRequest().authenticated())
                .oauth2ResourceServer(c -> c
                        .jwt(j -> j
                                .jwkSetUri(keySetUri)
                                .jwtAuthenticationConverter(converter)))
                .sessionManagement(c -> c
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("http://localhost:5173");
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
