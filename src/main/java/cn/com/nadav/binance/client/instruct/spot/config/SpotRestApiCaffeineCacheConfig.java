package cn.com.nadav.binance.client.instruct.spot.config;

import cn.com.nadav.binance.client.instruct.spot.properties.CaffeineCacheProperty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration(proxyBeanMethods = false)
@ConfigurationProperties(prefix = "spot.rest.api")
public class SpotRestApiCaffeineCacheConfig {

    private CaffeineCacheProperty cache;

}
