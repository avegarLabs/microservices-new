package com.avegarlabs.costosservice.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Arrays.asList(
                new ConcurrentMapCache("workers"),
                new ConcurrentMapCache("activities"),
                new ConcurrentMapCache("datNomValues"),
                new ConcurrentMapCache("departments"),
                new ConcurrentMapCache("datNom"),
                new ConcurrentMapCache("pr3Month"),
                new ConcurrentMapCache("sales"),
                new ConcurrentMapCache("coninsu")

        ));
        return cacheManager;
    }
}
