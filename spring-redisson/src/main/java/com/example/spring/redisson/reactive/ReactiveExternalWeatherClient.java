package com.example.spring.redisson.reactive;

import java.util.concurrent.ThreadLocalRandom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class ReactiveExternalWeatherClient {

    public Mono<Weather> callExternal(String zip) {
        log.info("Inside ReactiveExternalWeatherClient callExternal for zip = " + zip);
        return Mono.just(new Weather(zip, ThreadLocalRandom.current().nextInt(20, 30)));
    }
}
