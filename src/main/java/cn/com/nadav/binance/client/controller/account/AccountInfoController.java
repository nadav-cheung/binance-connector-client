package cn.com.nadav.binance.client.controller.account;

import cn.com.nadav.binance.client.controller.account.request.GetAccountRequest;
import cn.com.nadav.binance.client.service.AccountInfoService;
import cn.com.nadav.binance.client.service.ExchangeInfoService;
import cn.com.nadavframework.web.rest.ApiResponse;
import com.binance.connector.client.spot.rest.model.GetAccountResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountInfoController {


    private AccountInfoService accountInfoService;
    private ExchangeInfoService exchangeInfoService;

    public AccountInfoController(AccountInfoService accountInfoService, ExchangeInfoService exchangeInfoService) {
        this.accountInfoService = accountInfoService;
        this.exchangeInfoService = exchangeInfoService;
    }

    @PostMapping("/info")
    public ApiResponse<GetAccountResponse> queryAccountInfo(@RequestBody GetAccountRequest getAccountRequest) {
        GetAccountResponse getAccountResponse = accountInfoService.queryAccountDetailsInfo(getAccountRequest);
        return ApiResponse.success(getAccountResponse);
    }


}
