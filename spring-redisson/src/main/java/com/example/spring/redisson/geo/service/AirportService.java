package com.example.spring.redisson.geo.service;

import com.example.spring.redisson.geo.dto.Airport;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.GeoUnit;
import org.redisson.api.RGeoReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.api.geo.GeoSearchArgs;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@Slf4j
public class AirportService {

    private RGeoReactive<Airport> geo;

    public AirportService(RedissonReactiveClient client) {
        this.geo = client.getGeo("airports", new TypedJsonJacksonCodec(Airport.class));
    }

    public Flux<Airport> search(double lon, double lat, int searchRadiusInKms){
        GeoSearchArgs geoSearchArgs = GeoSearchArgs.from(lon, lat).radius(searchRadiusInKms, GeoUnit.KILOMETERS);
        return this.geo.search(geoSearchArgs)
                .flatMapIterable(Function.identity());
    }

}
