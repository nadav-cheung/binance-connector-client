package cn.com.nadav.binance.client.controller.account;

import cn.com.nadav.binance.client.controller.account.request.GetAccountRequest;
import cn.com.nadav.binance.client.service.SpotRestApiCacheService;
import cn.com.nadavframework.web.rest.ApiResponse;
import com.binance.connector.client.spot.rest.model.GetAccountResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountInfoController {


    private SpotRestApiCacheService spotRestApiCacheService;

    public AccountInfoController(SpotRestApiCacheService spotRestApiCacheService) {
        this.spotRestApiCacheService = spotRestApiCacheService;
    }

    @PostMapping("/info")
    public ApiResponse<GetAccountResponse> queryAccountInfo(@RequestBody GetAccountRequest getAccountRequest) {
        GetAccountResponse getAccountResponse = spotRestApiCacheService.queryAccountDetailsInfo(getAccountRequest);
        return ApiResponse.success(getAccountResponse);
    }


}
