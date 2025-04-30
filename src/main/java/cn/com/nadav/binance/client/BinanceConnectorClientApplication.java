package cn.com.nadav.binance.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@SpringBootApplication
public class BinanceConnectorClientApplication {


    public static void main(String[] args) {
        SpringApplication.run(BinanceConnectorClientApplication.class, args);
    }

}
