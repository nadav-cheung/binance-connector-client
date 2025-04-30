package cn.com.nadav.binance.client.service;

import cn.com.nadav.binance.client.controller.account.request.AccountInfoRequest;
import cn.com.nadav.binance.client.controller.account.request.GetPriceRequest;
import cn.hutool.core.map.MapUtil;
import com.binance.connector.client.common.ApiResponse;
import com.binance.connector.client.spot.rest.api.SpotRestApi;
import com.binance.connector.client.spot.rest.model.ExchangeInfoResponse;
import com.binance.connector.client.spot.rest.model.ExchangeInfoResponseSymbolsInner;
import com.binance.connector.client.spot.rest.model.Symbols;
import com.binance.connector.client.spot.rest.model.TickerPriceResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ExchangeInfoService {


    private final List<@Valid ExchangeInfoResponseSymbolsInner> exchangeSymbols = Lists.newArrayList();


    // btc
    // usdt --> ExchangeInfoResponseSymbolsInner
    // eth  --> ExchangeInfoResponseSymbolsInner
    private final Map<String, Map<String, ExchangeInfoResponseSymbolsInner>> exchangeSymbolMap = MapUtil.newConcurrentHashMap();


    private static final String USDT = "USDT";

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

    public ExchangeInfoResponse queryExchangeInfo(AccountInfoRequest accountInfoRequest) {
        SpotRestApi spotRestApi = spotRestApiCacheService.quireSpotRestApi(accountInfoRequest.getApiKey(), accountInfoRequest.getSecretKey(), accountInfoRequest.getUrl());
        ApiResponse<ExchangeInfoResponse> exchangeInfoResponseApiResponse = spotRestApi.exchangeInfo(null, null, null, null, null);
        return exchangeInfoResponseApiResponse.getData();
    }


    public ExchangeInfoResponse buildExchangeInfoCache(AccountInfoRequest accountInfoRequest) {
        SpotRestApi spotRestApi = spotRestApiCacheService.quireSpotRestApi(accountInfoRequest.getApiKey(), accountInfoRequest.getSecretKey(), accountInfoRequest.getUrl());
        ApiResponse<ExchangeInfoResponse> exchangeInfoResponseApiResponse = spotRestApi.exchangeInfo(null, null, null, null, null);
        ExchangeInfoResponse exchangeInfo = exchangeInfoResponseApiResponse.getData();
        exchangeSymbols.clear();
        exchangeSymbols.addAll(exchangeInfo.getSymbols());
        buildExchangeInfoMap();
        return exchangeInfo;
    }


    private void buildExchangeInfoMap() {

        for (ExchangeInfoResponseSymbolsInner exchangeSymbol : exchangeSymbols) {
            // 交易对 BTCUSDT
            String symbol = exchangeSymbol.getSymbol();

            // 交易对 BTC
            String baseAsset = exchangeSymbol.getBaseAsset();

            // 交易对 USDT
            String quoteAsset = exchangeSymbol.getQuoteAsset();

            exchangeSymbolMap.computeIfAbsent(baseAsset, k -> MapUtil.newHashMap()).put(quoteAsset, exchangeSymbol);
        }

        log.info("buildExchangeInfoMap: {}", exchangeSymbolMap);
    }


    public Map<String, ExchangeInfoResponseSymbolsInner> getExchangeSymbolMap(String assets) {
        if (StringUtils.isEmpty(assets)) {
            return MapUtil.newHashMap();
        }

        return exchangeSymbolMap.getOrDefault(assets, MapUtil.newHashMap());
    }


    public String queryUSDTSymbols(String assets) {
        return querySymbols(assets, USDT);
    }


    public String querySymbols(String assets, String quoteAsset) {
        if (StringUtils.isEmpty(assets) || StringUtils.isEmpty(quoteAsset)) {
            return null;
        }

        Map<String, ExchangeInfoResponseSymbolsInner> symbolMapOrDefault = exchangeSymbolMap.getOrDefault(assets, MapUtil.newHashMap());
        ExchangeInfoResponseSymbolsInner quotAssetSymbol = symbolMapOrDefault.get(quoteAsset);
        if (quotAssetSymbol == null) {
            return null;
        } else {
            return quotAssetSymbol.getSymbol();
        }
    }


}
