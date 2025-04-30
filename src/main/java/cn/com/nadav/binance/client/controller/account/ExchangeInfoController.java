package cn.com.nadav.binance.client.controller.account;

import cn.com.nadav.binance.client.controller.account.request.AccountInfoRequest;
import cn.com.nadav.binance.client.service.AccountInfoService;
import cn.com.nadav.binance.client.service.ExchangeInfoService;
import cn.com.nadavframework.web.rest.ApiResponse;
import com.binance.connector.client.spot.rest.model.ExchangeInfoResponse;
import com.binance.connector.client.spot.rest.model.GetAccountResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exchange")
public class ExchangeInfoController {


    private AccountInfoService accountInfoService;
    private ExchangeInfoService exchangeInfoService;

    public ExchangeInfoController(AccountInfoService accountInfoService, ExchangeInfoService exchangeInfoService) {
        this.accountInfoService = accountInfoService;
        this.exchangeInfoService = exchangeInfoService;
    }

    @PostMapping("/info")
    public ApiResponse<ExchangeInfoResponse> queryAccountInfo(@RequestBody AccountInfoRequest accountInfoRequest) {
        ExchangeInfoResponse exchangeInfoResponse = exchangeInfoService.queryExchangeInfo(accountInfoRequest);
        return ApiResponse.success(exchangeInfoResponse);
    }


    @PostMapping("/refresh/cache")
    public ApiResponse<ExchangeInfoResponse> refreshCache(@RequestBody AccountInfoRequest accountInfoRequest) {
        ExchangeInfoResponse exchangeInfoResponse = exchangeInfoService.buildExchangeInfoCache(accountInfoRequest);
        return ApiResponse.success(exchangeInfoResponse);
    }



}
