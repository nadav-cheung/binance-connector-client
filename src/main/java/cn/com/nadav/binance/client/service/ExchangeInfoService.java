package cn.com.nadav.binance.client.service;

import cn.com.nadav.binance.client.controller.account.request.GetPriceRequest;
import com.binance.connector.client.common.ApiResponse;
import com.binance.connector.client.spot.rest.api.SpotRestApi;
import com.binance.connector.client.spot.rest.model.Symbols;
import com.binance.connector.client.spot.rest.model.TickerPriceResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ExchangeInfoService {


    private SpotRestApiCacheService spotRestApiCacheService;

    public ExchangeInfoService(SpotRestApiCacheService spotRestApiCacheService) {
        this.spotRestApiCacheService = spotRestApiCacheService;
    }


    public TickerPriceResponse queryCurrentPrice(GetPriceRequest getPriceRequest) {
        SpotRestApi spotRestApi = spotRestApiCacheService.quireSpotRestApi(getPriceRequest.getApiKey(), getPriceRequest.getSecretKey(), getPriceRequest.getUrl());
        Symbols symbols = new Symbols();
        symbols.addAll(getPriceRequest.getSymbols());
        if (StringUtils.isNotEmpty(getPriceRequest.getSymbol())) {
            symbols.add(getPriceRequest.getSymbol());
        }
        ApiResponse<TickerPriceResponse> tickerPriceResponse = spotRestApi.tickerPrice(null, symbols);
        return tickerPriceResponse.getData();
    }

}
