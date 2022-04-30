package com.example.spring.postgres.redis.reactive.controller;

import com.example.spring.postgres.redis.reactive.dto.ProductDto;
import com.example.spring.postgres.redis.reactive.repository.Product;

public class ProductDtoConvertor {

    public static Product convert(ProductDto productDto){
        return Product.builder().id(productDto.getId())
                .name(productDto.getName())
                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .build();
    }

    public static ProductDto convert(Product product){
        return ProductDto.builder().id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
