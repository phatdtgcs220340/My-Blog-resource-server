package com.phatdo.blog.resourceserver.utils.configuration.cache;

import com.phatdo.blog.resourceserver.user.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableRedisRepositories
public class RedisConfig {

    @Bean
    public RedisTemplate<String, User> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, User> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        // Set key serializer
        template.setKeySerializer(new StringRedisSerializer());

        // Set value serializer
        Jackson2JsonRedisSerializer<User> serializer = new Jackson2JsonRedisSerializer<>(User.class);
        template.setValueSerializer(serializer);
        return template;
    }
}
