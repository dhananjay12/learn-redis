package com.example.spring.postgres.redis.reactive.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ProductRepository  extends ReactiveCrudRepository<Product, Integer> {

    //Only for postgres.
    @Query("ALTER SEQUENCE product_id_seq RESTART WITH 1")
    Mono<Void> clearCounterToOne();
}
