package me.daniel.taskapi.search.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Configuration
@EnableCaching(proxyTargetClass = true)
public class CacheConfiguration extends CachingConfigurerSupport {
    @Configuration
    public class CacheConfig {
        @Bean
        public CacheManager cacheManager() {
            SimpleCacheManager cacheManager = new SimpleCacheManager();
            List<CaffeineCache> caches = Arrays.stream(CacheType.values())
                    .map(cache -> new CaffeineCache(cache.getCacheName(), Caffeine.newBuilder().recordStats()
                            .expireAfterWrite(cache.getExpiredAfterWrite(), TimeUnit.SECONDS)
                            .maximumSize(cache.getMaximumSize())
                            .build())
                    )
                    .collect(Collectors.toList());
            cacheManager.setCaches(caches);
            return cacheManager;
        }
    }
}
