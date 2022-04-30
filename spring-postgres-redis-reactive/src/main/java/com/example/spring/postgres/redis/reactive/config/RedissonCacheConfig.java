package com.example.spring.postgres.redis.reactive.config;

import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.redisson.spring.starter.RedissonAutoConfigurationCustomizer;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonCacheConfig {

    @Bean
    public CacheManager cacheManager(RedissonClient redissonClient){
        return new RedissonSpringCacheManager(redissonClient);
    }

    // Following is required if you have cache objects that are complex json type.
    // If you are just using basic types, this is not a hard requirement.
    @Bean
    public RedissonAutoConfigurationCustomizer redisConfigCustomizer(){
        return config -> config.setCodec(new JsonJacksonCodec());
    }


}
