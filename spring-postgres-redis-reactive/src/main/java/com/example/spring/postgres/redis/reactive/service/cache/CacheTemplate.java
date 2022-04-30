package com.example.spring.postgres.redis.reactive.service.cache;

import reactor.core.publisher.Mono;

// To abstract the search logic from cache and then from DB (updating cache too)
// Ok to skip this if we just have one service class with this impl.
// Here we have 2
public abstract class CacheTemplate<K,E> {
    public Mono<E> get(K key){
        return getFromCache(key)
                .switchIfEmpty(
                        getFromSource(key)
                                .flatMap(e -> updateCache(key, e))
                );
    }

    public Mono<E> update(K key, E E){
        return updateSource(key, E)
                .flatMap(e -> deleteFromCache(key).thenReturn(e));
    }

    public Mono<Void> delete(K key){
        return deleteFromSource(key)
                .then(deleteFromCache(key));
    }

    abstract protected Mono<E> getFromSource(K key);
    abstract protected Mono<E> getFromCache(K key);
    abstract protected Mono<E> updateSource(K key, E E);
    abstract protected Mono<E> updateCache(K key, E E);
    abstract protected Mono<Void> deleteFromSource(K key);
    abstract protected Mono<Void> deleteFromCache(K key);

}
