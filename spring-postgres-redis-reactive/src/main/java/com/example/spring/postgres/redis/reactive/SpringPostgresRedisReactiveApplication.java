package com.example.spring.postgres.redis.reactive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SpringPostgresRedisReactiveApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringPostgresRedisReactiveApplication.class, args);
	}

}
