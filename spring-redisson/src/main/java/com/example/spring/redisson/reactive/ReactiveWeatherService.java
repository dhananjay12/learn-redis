package com.example.spring.redisson.reactive;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RMapReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class ReactiveWeatherService {

    private ReactiveExternalWeatherClient reactiveExternalWeatherClient;

    private RMapReactive<String,Weather> weatherRMapReactive;

    public ReactiveWeatherService(ReactiveExternalWeatherClient reactiveExternalWeatherClient,
                                  RedissonReactiveClient redissonReactiveClient) {
        this.reactiveExternalWeatherClient = reactiveExternalWeatherClient;
        this.weatherRMapReactive = redissonReactiveClient.getMap("reactiveWeather",
                new TypedJsonJacksonCodec(String.class,Weather.class));
    }

    public Mono<Weather> getWeatherInfo(final String zip){
        return this.weatherRMapReactive.get(zip) //First check in Cache
                .switchIfEmpty(// If its not in cache do these
                        // Defer is important else, it will always go inside switchIfEmpty
                        //https://stackoverflow.com/questions/54373920/mono-switchifempty-is-always-called
                        Mono.defer(()-> this.reactiveExternalWeatherClient.callExternal(zip))
                         // Call external service to get data
                                .flatMap(data-> this.weatherRMapReactive.put(zip,data) // Put result in cache
                                        .thenReturn(data)) // Return the result
                ); // No need to subscribe as the subscriber will be the client who calls the REST endpoint
    }
}
