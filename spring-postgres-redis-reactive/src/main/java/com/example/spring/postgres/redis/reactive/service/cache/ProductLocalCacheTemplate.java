package com.example.spring.postgres.redis.reactive.service.cache;

import com.example.spring.postgres.redis.reactive.repository.Product;
import com.example.spring.postgres.redis.reactive.repository.ProductRepository;
import org.redisson.api.LocalCachedMapOptions;
import org.redisson.api.RLocalCachedMap;
import org.redisson.api.RedissonClient;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ProductLocalCacheTemplate extends CacheTemplate<Integer, Product> {
    private ProductRepository repository;
    private RLocalCachedMap<Integer, Product> map;

    public ProductLocalCacheTemplate(ProductRepository repository, RedissonClient redissonClient) {
        this.repository = repository;
        LocalCachedMapOptions<Integer, Product> mapOptions = LocalCachedMapOptions.<Integer, Product>defaults()
                .syncStrategy(LocalCachedMapOptions.SyncStrategy.UPDATE)
                .reconnectionStrategy(LocalCachedMapOptions.ReconnectionStrategy.CLEAR);
        this.map = redissonClient.getLocalCachedMap("product-local-cache",
                new TypedJsonJacksonCodec(Integer.class, Product.class), mapOptions);
    }

    @Override
    protected Mono<Product> getFromSource(Integer id) {
        return this.repository.findById(id);
    }

    @Override
    protected Mono<Product> getFromCache(Integer id) {
        return Mono.justOrEmpty(this.map.get(id));
    }

    @Override
    protected Mono<Product> updateSource(Integer id, Product product) {
        return this.repository.findById(id)
                .doOnNext(p -> product.setId(id))
                .flatMap(p -> this.repository.save(product));
    }

    @Override
    protected Mono<Product> updateCache(Integer id, Product product) {
        return Mono.create(sink ->
                this.map.fastPutAsync(id, product)
                        .thenAccept(b -> sink.success(product))
                        .exceptionally(ex -> {
                            sink.error(ex);
                            return null;
                        })
        );
    }

    @Override
    protected Mono<Void> deleteFromSource(Integer id) {
        return this.repository.deleteById(id); // If its not applicable, just return  Mono.empty()
    }

    @Override
    protected Mono<Void> deleteFromCache(Integer id) {
        return Mono.create(sink ->
                this.map.fastRemoveAsync(id)
                        .thenAccept(b -> sink.success())
                        .exceptionally(ex -> {
                            sink.error(ex);
                            return null;
                        })
        );
    }
}

