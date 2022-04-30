package com.example.spring.redisson.geo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true) //Because we dont want all variable
public class Airport {

    private String code;
    private String name;
    private String city;
    private String country;
    private double lat;
    private double lon;
}
