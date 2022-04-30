package com.example.spring.postgres.redis.reactive.service;

import com.example.spring.postgres.redis.reactive.repository.Product;
import com.example.spring.postgres.redis.reactive.repository.ProductRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Mono<Product> getProduct(int id) {
        return this.productRepository.findById(id);
    }

    public Mono<Product> updateProduct(int id, Mono<Product> product) {
        return this.productRepository.findById(id)
                .flatMap(dbData ->  product.doOnNext(p -> p.setId(id)))
                .flatMap(this.productRepository::save);
    }
}
