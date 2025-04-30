package cn.com.nadav.binance.client.instruct.spot.config;

import com.binance.connector.client.spot.rest.api.SpotRestApi;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Data
@Configuration(proxyBeanMethods = false)
public class SpotRestApiCaffeineConfig {

    private SpotRestApiCaffeineCacheConfig spotRestApiCaffeineCacheConfig;

    public SpotRestApiCaffeineConfig(SpotRestApiCaffeineCacheConfig spotRestApiCaffeineCacheConfig) {
        this.spotRestApiCaffeineCacheConfig = spotRestApiCaffeineCacheConfig;
    }

    @Bean
    public Cache<String, SpotRestApi> SpotRestApiCache() {
        return Caffeine.newBuilder()
                .expireAfterAccess(spotRestApiCaffeineCacheConfig.getCache().getExpireAfterAccessSecond(), TimeUnit.SECONDS)
                .maximumSize(spotRestApiCaffeineCacheConfig.getCache().getMaximumSize())
                .expireAfterWrite(spotRestApiCaffeineCacheConfig.getCache().getExpireAfterWriteSecond(), TimeUnit.SECONDS)
                .initialCapacity(spotRestApiCaffeineCacheConfig.getCache().getInitialCapacity())
                .build();
    }

}
