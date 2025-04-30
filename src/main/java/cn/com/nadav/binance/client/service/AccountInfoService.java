package cn.com.nadav.binance.client.service;

import cn.com.nadav.binance.client.controller.account.request.GetAccountRequest;
import cn.com.nadav.binance.client.controller.account.request.GetPriceRequest;
import com.binance.connector.client.common.ApiResponse;
import com.binance.connector.client.spot.rest.api.SpotRestApi;
import com.binance.connector.client.spot.rest.model.GetAccountResponse;
import com.binance.connector.client.spot.rest.model.Symbols;
import com.binance.connector.client.spot.rest.model.TickerPriceResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AccountInfoService {

    private SpotRestApiCacheService spotRestApiCacheService;

    public AccountInfoService(SpotRestApiCacheService spotRestApiCacheService) {
        this.spotRestApiCacheService = spotRestApiCacheService;
    }


    public GetAccountResponse queryAccountDetailsInfo(GetAccountRequest getAccountRequest) {
        SpotRestApi spotRestApi = spotRestApiCacheService.quireSpotRestApi(getAccountRequest.getApiKey(), getAccountRequest.getSecretKey(), getAccountRequest.getUrl());
        ApiResponse<GetAccountResponse> accountDetailsResponse = spotRestApi.getAccount(getAccountRequest.getOmitZeroBalances(), getAccountRequest.getReceiveWindow());
        return accountDetailsResponse.getData();
    }




}
