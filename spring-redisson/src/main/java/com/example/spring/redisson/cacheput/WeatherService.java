package com.example.spring.redisson.cacheput;


import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WeatherService {

    final ExternalServiceClient externalServiceClient;

    public WeatherService(ExternalServiceClient externalServiceClient) {
        this.externalServiceClient = externalServiceClient;
    }

    @Cacheable(value = "weather", key = "#zip")
    public Weather getWeatherData(int zip) {
        log.info("Inside getWeatherData");
        return null;
    }

    //@Scheduled(fixedRate = 300_000)
    public void updateCacheWithTheseZips() {
        log.info("Inside updateCacheWithTheseZips");
        IntStream.rangeClosed(100, 150) // Or some other way to updateAll
                .forEach(this.externalServiceClient::getWeatherInfo);
    }
}
