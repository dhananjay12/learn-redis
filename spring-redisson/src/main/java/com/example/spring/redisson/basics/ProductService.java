package com.example.spring.redisson.basics;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProductService {

    private static Map<Integer, Product> storage= new ConcurrentHashMap<>();

    static {
        storage.put(1,new Product(1,"Ps5", "Sony Gaming Console", 399.00));
        storage.put(2,new Product(2,"Xbox S", "Microsoft Gaming Console", 299.00));
        storage.put(3,new Product(3,"Nintendo Lite", "Sony Gaming Console", 199.00));
    }

    @Cacheable(value = "product", key = "#id")
    public Product getProduct(int id){
        log.info("Inside getProduct");
        fakeTimeout(2000);
        return storage.get(id);
    }

    private void fakeTimeout(int milliSeconds){
        try {
            Thread.sleep(milliSeconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @CacheEvict(value = "product", key = "#id")
    public Product updateProduct(int id, Product product){
        log.info("Inside updateProduct");
        return storage.put(id,product);
    }

    //  @Scheduled(fixedRate = 10_000)
    @CacheEvict(value = "product", allEntries = true)
    public void clearCache(){
        log.info("clearing all product keys");
    }
}
