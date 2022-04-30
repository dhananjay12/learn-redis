package com.example.spring.postgres.redis.reactive.dto;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ProductDto {

    private Integer id;
    private String name;
    private String description;
    private double price;
}
