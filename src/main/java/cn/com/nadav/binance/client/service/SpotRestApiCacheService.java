package cn.com.nadav.binance.client.service;

import cn.com.nadav.binance.client.controller.account.request.GetAccountRequest;
import cn.com.nadav.binance.client.controller.account.request.GetPriceRequest;
import com.binance.connector.client.common.ApiResponse;
import com.binance.connector.client.common.configuration.ClientConfiguration;
import com.binance.connector.client.common.configuration.SignatureConfiguration;
import com.binance.connector.client.spot.rest.SpotRestApiUtil;
import com.binance.connector.client.spot.rest.api.SpotRestApi;
import com.binance.connector.client.spot.rest.model.GetAccountResponse;
import com.binance.connector.client.spot.rest.model.Symbols;
import com.binance.connector.client.spot.rest.model.TickerPriceResponse;
import com.github.benmanes.caffeine.cache.Cache;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SpotRestApiCacheService {

    private Cache<String, SpotRestApi> SpotRestApiCache;


    public SpotRestApiCacheService(Cache<String, SpotRestApi> SpotRestApiCache) {
        this.SpotRestApiCache = SpotRestApiCache;
    }

    // 复合 key（例如 apiKey + secretKey）
    private static String buildKey(String apiKey, String secretKey, String url) {
        StringBuilder sb = new StringBuilder();
        sb.append(apiKey).append("|").append(secretKey);
        if (StringUtils.isNotEmpty(url)) {
            sb.append("|").append(url);
        }
        return sb.toString();
    }

    public SpotRestApi quireSpotRestApi(String apiKey, String secretKey, String url) {
        String key = buildKey(apiKey, secretKey, url);


        return SpotRestApiCache.get(key, k -> {
            SignatureConfiguration signatureConfiguration = new SignatureConfiguration();
            signatureConfiguration.setApiKey(apiKey);
            signatureConfiguration.setSecretKey(secretKey);

            ClientConfiguration clientConfiguration = new ClientConfiguration();
            if (StringUtils.isNotEmpty(url)) {
                clientConfiguration.setUrl(url);
            }

            clientConfiguration.setSignatureConfiguration(signatureConfiguration);

            return new SpotRestApi(SpotRestApiUtil.getDefaultClient(clientConfiguration));
        });

    }

    public GetAccountResponse queryAccountDetailsInfo(GetAccountRequest getAccountRequest) {
        SpotRestApi spotRestApi = quireSpotRestApi(getAccountRequest.getApiKey(), getAccountRequest.getSecretKey(), getAccountRequest.getUrl());
        ApiResponse<GetAccountResponse> accountDetailsResponse = spotRestApi.getAccount(getAccountRequest.getOmitZeroBalances(), getAccountRequest.getReceiveWindow());
        return accountDetailsResponse.getData();
    }


    public TickerPriceResponse queryCurrentPrice(GetPriceRequest getPriceRequest) {
        SpotRestApi spotRestApi = quireSpotRestApi(getPriceRequest.getApiKey(), getPriceRequest.getSecretKey(), getPriceRequest.getUrl());
        Symbols symbols = new Symbols();
        symbols.addAll(getPriceRequest.getSymbols());
        if (StringUtils.isNotEmpty(getPriceRequest.getSymbol())) {
            symbols.add(getPriceRequest.getSymbol());
        }
        ApiResponse<TickerPriceResponse> tickerPriceResponse = spotRestApi.tickerPrice(null, symbols);
        return tickerPriceResponse.getData();
    }


}
