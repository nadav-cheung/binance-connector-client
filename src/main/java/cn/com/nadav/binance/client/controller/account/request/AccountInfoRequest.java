package cn.com.nadav.binance.client.controller.account.request;

import lombok.Data;

@Data
public class AccountInfoRequest {

    private String apiKey;
    private String secretKey;
    private String url;
}
