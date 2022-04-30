package com.example.spring.postgres.redis.reactive.config;

import com.example.spring.postgres.redis.reactive.repository.Product;
import com.example.spring.postgres.redis.reactive.repository.ProductRepository;
import java.util.concurrent.ThreadLocalRandom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
@Slf4j
@RequiredArgsConstructor
public class SampleDataInitializer {

    private final ProductRepository productRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void ready(){
        log.info("==============SampleDataInitializer ready===============");
        Flux<Product> productInsert = Flux.range(1, 10)
                .map(i -> new Product(null,"Product "+ i, "Product Description "+ i,
                        ThreadLocalRandom.current().nextInt(100,10000)/100d)) // For 2 decimal
                .flatMap(p -> this.productRepository.save(p));

        this.productRepository
                .deleteAll().block();
        this.productRepository
                .clearCounterToOne()
        .block();

        productInsert
                .thenMany(this.productRepository.findAll())
                .subscribe(product -> log.info(product.toString()));

    }
}
