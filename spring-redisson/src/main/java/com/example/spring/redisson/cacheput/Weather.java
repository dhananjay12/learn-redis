package com.example.spring.redisson.cacheput;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Weather {

    private Integer zip;
    private double temp;
}
