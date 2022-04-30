package com.example.spring.postgres.redis.reactive.controller;

import com.example.spring.postgres.redis.reactive.dto.ProductDto;
import com.example.spring.postgres.redis.reactive.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("product")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<ProductDto>> product(@PathVariable("id") int id) {
        return productService.getProduct(id)
                .map(product -> ResponseEntity.ok().body(
                        ProductDtoConvertor.convert(product)))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<ProductDto>> updateProduct(@PathVariable("id") int id, @RequestBody ProductDto productDto) {

        return productService.updateProduct(id, Mono.just(ProductDtoConvertor.convert(productDto)))
                .map(product -> ResponseEntity.ok().body(
                        ProductDtoConvertor.convert(product)))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}
