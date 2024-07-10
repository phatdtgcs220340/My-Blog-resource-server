//package com.phatdo.blog.resourceserver.configuration.jwt;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.security.crypto.encrypt.KeyStoreKeyFactory;
//
//@Configuration
//public class JwtConfig {
//    @Value("${jwt.keystore.path}")
//    private String keystorePath;
//    @Value("${jwt.keystore.password}")
//    private String keystorePassword;
//    @Value("${jwt.key.alias}")
//    private String keyAlias;
//    @Value("${jwt.key.password}")
//    private String keyPassword;
//
//    @Bean
//    public JwtAccessTokenConverter accessTokenConverter() {
//        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//        converter.setKeyPair(keyStoreKeyFactory().getKeyPair(keyAlias));
//        return converter;
//    }
//
//    @Bean
//    public KeyStoreKeyFactory keyStoreKeyFactory() {
//        return new KeyStoreKeyFactory(new ClassPathResource(keystorePath), keystorePassword.toCharArray());
//    }
//}
