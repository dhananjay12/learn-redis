package com.example.spring.postgres.redis.reactive.repository;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Product {

    @Id
    private Integer id;
    private String name;
    private String description;
    private double price;
}
