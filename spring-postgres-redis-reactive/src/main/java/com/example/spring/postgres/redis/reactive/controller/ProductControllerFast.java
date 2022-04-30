package com.example.spring.postgres.redis.reactive.controller;

import com.example.spring.postgres.redis.reactive.dto.ProductDto;
import com.example.spring.postgres.redis.reactive.service.ProductServiceCache;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("product/fast")
public class ProductControllerFast {

    private ProductServiceCache productServiceCache;

    public ProductControllerFast(ProductServiceCache productServiceCache) {
        this.productServiceCache = productServiceCache;
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<ProductDto>> product(@PathVariable("id") int id) {
        return productServiceCache.getProduct(id)
                .map(product -> ResponseEntity.ok().body(
                        ProductDtoConvertor.convert(product)))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<ProductDto>> updateProduct(@PathVariable("id") int id, @RequestBody ProductDto productDto) {

        return productServiceCache.updateProduct(id, Mono.just(ProductDtoConvertor.convert(productDto)))
                .map(product -> ResponseEntity.ok().body(
                        ProductDtoConvertor.convert(product)))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}
