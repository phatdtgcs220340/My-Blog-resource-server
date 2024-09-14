package com.phatdo.blog.resourceserver.utils.configuration.security;

import com.phatdo.blog.resourceserver.utils.configuration.jwt.JwtConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import static com.phatdo.blog.resourceserver.utils.commons.path.CommonApi.*;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    @Value("${spring.security.oauth2.resource-server.jwt.jwk-set-uri}")
    private String keySetUri;
    @Value("${oauth2-client.domain}")
    private String clientDomain;
    private final JwtConverter converter;

    @Autowired
    public SecurityConfig(JwtConverter converter) {
        this.converter = converter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(c -> c
                        .requestMatchers(HttpMethod.GET, API_BLOG, String.format("%s/**",API_BLOG)).permitAll()
                        .requestMatchers(HttpMethod.GET, API_REPLY).permitAll()
                        .requestMatchers(HttpMethod.GET, API_TAG, "/api/v1/tag/**").permitAll()
                        .requestMatchers(HttpMethod.GET, API_REGISTER).permitAll()
                        .requestMatchers(HttpMethod.GET, String.format("%s/swagger-ui/**",API_VERSION_1)).permitAll()
                        .requestMatchers(HttpMethod.POST, String.format("%s/list",API_BLOG)).permitAll()
                        .anyRequest().authenticated())
                .oauth2ResourceServer(c -> c
                        .jwt(j -> j
                                .jwkSetUri(keySetUri)
                                .jwtAuthenticationConverter(converter)))
                .sessionManagement(c -> c
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin(clientDomain);
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
