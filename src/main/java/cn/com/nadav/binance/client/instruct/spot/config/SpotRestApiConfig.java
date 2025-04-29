package cn.com.nadav.binance.client.instruct.spot.config;

import com.binance.connector.client.common.configuration.ClientConfiguration;
import com.binance.connector.client.common.configuration.SignatureConfiguration;
import com.binance.connector.client.common.websocket.configuration.WebSocketClientConfiguration;
import com.binance.connector.client.spot.rest.api.SpotRestApi;
import com.binance.connector.client.spot.websocket.stream.SpotWebSocketStreamsUtil;
import com.binance.connector.client.spot.websocket.stream.api.SpotWebSocketStreams;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpotRestApiConfig {

    public SpotRestApi spotRestApi() {
        SignatureConfiguration signatureConfiguration = new SignatureConfiguration();
        signatureConfiguration.setApiKey("apiKey");
        signatureConfiguration.setSecretKey("secretKey");

        ClientConfiguration clientConfiguration = new ClientConfiguration();
        clientConfiguration.setUrl("https://api.binance.com");
        clientConfiguration.setSignatureConfiguration(signatureConfiguration);
        SpotRestApi spotRestApi = new SpotRestApi(clientConfiguration);
        spotRestApi.getAccount()
        return spotRestApi;
    }


    public SpotWebSocketStreams spotWebSocketStreams() {

        // Basic client config
        WebSocketClientConfiguration clientConfiguration = SpotWebSocketStreamsUtil.getClientConfiguration();
        // set usePool flag to true
        clientConfiguration.setUsePool(true);
        // Use the api
        SpotWebSocketStreams api = new SpotWebSocketStreams(clientConfiguration);
    }


}
