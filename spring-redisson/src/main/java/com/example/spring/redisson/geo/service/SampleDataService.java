package com.example.spring.redisson.geo.service;

import com.example.spring.redisson.geo.dto.Airport;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RGeoReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@Slf4j
public class SampleDataService {

    private RGeoReactive<Airport> geo;

    private RedissonReactiveClient client;

    public SampleDataService(RedissonReactiveClient client) {
        this.client = client;
        this.geo = this.client.getGeo("airports", new TypedJsonJacksonCodec(Airport.class));
    }

    public void sampleData(){
        Flux.fromIterable(getAirports())
                //Store lat-lon and the object
                .flatMap(airport -> this.geo.add(airport.getLon(), airport.getLat(), airport)
                        .thenReturn(airport))
                .doFinally(s -> log.info(s.toString()))
                .subscribe();
    }

    private List<Airport> getAirports(){
        ObjectMapper mapper = new ObjectMapper();
        InputStream stream = SampleDataService.class.getClassLoader()
                .getResourceAsStream("airports_sample_data.json");
        try {
            return mapper.readValue(stream, new TypeReference<List<Airport>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
