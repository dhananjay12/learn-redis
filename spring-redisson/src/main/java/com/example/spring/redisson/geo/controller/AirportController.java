package com.example.spring.redisson.geo.controller;

import com.example.spring.redisson.geo.dto.Airport;
import com.example.spring.redisson.geo.service.AirportService;
import com.example.spring.redisson.geo.service.SampleDataService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("airport")
public class AirportController {

    private SampleDataService sampleDataService;
    private AirportService airportService;

    public AirportController(SampleDataService sampleDataService, AirportService airportService) {
        this.sampleDataService = sampleDataService;
        this.airportService = airportService;
    }

    @GetMapping("/search")
    public Flux<Airport> search(@RequestParam double lat, @RequestParam double lon,
                                @RequestParam(defaultValue = "50") int radius){
        return airportService.search(lon,lat,radius);
    }

    @PostMapping("/init")
    public Mono<Void> createData(){
        sampleDataService.sampleData();
        return Mono.empty();
    }
}
