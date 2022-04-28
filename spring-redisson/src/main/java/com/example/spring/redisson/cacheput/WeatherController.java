package com.example.spring.redisson.cacheput;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cache-put")
public class WeatherController {

    private WeatherService productService;

    public WeatherController(WeatherService productService) {
        this.productService = productService;
    }

    @GetMapping("/weather/{zip}")
    public ResponseEntity<?> product(@PathVariable("zip") int zip) {
        Weather data = productService.getWeatherData(zip);
        if (data != null) {
            return ResponseEntity.ok(data);
        }
        return ResponseEntity.notFound().build();
    }

}
