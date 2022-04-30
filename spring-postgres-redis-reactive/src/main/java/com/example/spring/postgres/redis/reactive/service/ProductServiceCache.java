package com.example.spring.postgres.redis.reactive.service;

import com.example.spring.postgres.redis.reactive.repository.Product;
import com.example.spring.postgres.redis.reactive.repository.ProductRepository;
import com.example.spring.postgres.redis.reactive.service.cache.ProductCacheTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ProductServiceCache {

    private ProductCacheTemplate productCacheTemplate;

    public ProductServiceCache(ProductCacheTemplate productCacheTemplate) {
        this.productCacheTemplate = productCacheTemplate;
    }

    public Mono<Product> getProduct(int id){
        return this.productCacheTemplate.get(id);
    }

    public Mono<Product> updateProduct(int id, Mono<Product> productMono){
        return productMono
                .flatMap(p -> this.productCacheTemplate.update(id, p));
    }

    public Mono<Void> deleteProduct(int id){
        return this.productCacheTemplate.delete(id);
    }
}
