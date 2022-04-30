package com.example.spring.postgres.redis.reactive.service;

import com.example.spring.postgres.redis.reactive.repository.Product;
import com.example.spring.postgres.redis.reactive.service.cache.ProductLocalCacheTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ProductServiceLocalCache {

    private ProductLocalCacheTemplate productLocalCacheTemplate;

    public ProductServiceLocalCache(ProductLocalCacheTemplate productLocalCacheTemplate) {
        this.productLocalCacheTemplate = productLocalCacheTemplate;
    }

    public Mono<Product> getProduct(int id) {
        return this.productLocalCacheTemplate.get(id);
    }

    public Mono<Product> updateProduct(int id, Mono<Product> productMono) {
        return productMono
                .flatMap(p -> this.productLocalCacheTemplate.update(id, p));
    }

    public Mono<Void> deleteProduct(int id) {
        return this.productLocalCacheTemplate.delete(id);
    }
}
