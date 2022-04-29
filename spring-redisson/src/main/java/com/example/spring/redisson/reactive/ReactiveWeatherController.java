package com.example.spring.redisson.reactive;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/reactive")
public class ReactiveWeatherController {

    private ReactiveWeatherService reactiveWeatherService;

    public ReactiveWeatherController(ReactiveWeatherService reactiveWeatherService) {
        this.reactiveWeatherService = reactiveWeatherService;
    }

    @GetMapping("/weather/{zip}")
    public Mono<Weather> getWeatherInfo(@PathVariable String zip){
        return this.reactiveWeatherService.getWeatherInfo(zip);
    }

    @GetMapping("/weather/ttl/{zip}")
    public Mono<Weather> getWeatherInfoTTL(@PathVariable String zip){
        return this.reactiveWeatherService.getWeatherInfoTTL(zip);
    }
}
