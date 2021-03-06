package com.example.spring.redisson.reactive;

import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RMapCacheReactive;
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

    private RMapCacheReactive<String,Weather> weatherRMapCacheReactive;

    public ReactiveWeatherService(ReactiveExternalWeatherClient reactiveExternalWeatherClient,
                                  RedissonReactiveClient redissonReactiveClient) {
        this.reactiveExternalWeatherClient = reactiveExternalWeatherClient;
        this.weatherRMapReactive = redissonReactiveClient.getMap("reactiveWeather",
                new TypedJsonJacksonCodec(String.class,Weather.class));
        this.weatherRMapCacheReactive = redissonReactiveClient.getMapCache("ttlWeather",
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

    public Mono<Weather> getWeatherInfoTTL(final String zip){
        return this.weatherRMapCacheReactive.get(zip)
                .switchIfEmpty(
                        Mono.defer(()-> this.reactiveExternalWeatherClient.callExternal(zip))
                                .flatMap(data-> this.weatherRMapCacheReactive.put(zip,data, 10, TimeUnit.SECONDS)
                                        .thenReturn(data))
                );
    }
}
