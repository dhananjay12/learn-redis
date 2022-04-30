package com.example.spring.postgres.redis.reactive.controller;

import com.example.spring.postgres.redis.reactive.dto.ProductDto;
import com.example.spring.postgres.redis.reactive.service.ProductServiceLocalCache;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("product/more-fast")
public class ProductControllerMoreFast {

    private ProductServiceLocalCache productServiceLocalCache;

    public ProductControllerMoreFast(ProductServiceLocalCache productServiceLocalCache) {
        this.productServiceLocalCache = productServiceLocalCache;
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<ProductDto>> product(@PathVariable("id") int id) {
        return productServiceLocalCache.getProduct(id)
                .map(product -> ResponseEntity.ok().body(
                        ProductDtoConvertor.convert(product)))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<ProductDto>> updateProduct(@PathVariable("id") int id, @RequestBody ProductDto productDto) {

        return productServiceLocalCache.updateProduct(id, Mono.just(ProductDtoConvertor.convert(productDto)))
                .map(product -> ResponseEntity.ok().body(
                        ProductDtoConvertor.convert(product)))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}
