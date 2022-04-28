package com.example.spring.redisson.basics;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Product {

    private Integer id;
    private String name;
    private String description;
    private double price;
}
