package com.example.spring.redisson.cacheput;

import java.util.concurrent.ThreadLocalRandom;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

@Service
public class ExternalServiceClient {

    @CachePut(value = "weather", key = "#zip")
    public Weather getWeatherInfo(int zip) {

        return new Weather(zip, ThreadLocalRandom.current().nextInt(20, 30));
    }


}
