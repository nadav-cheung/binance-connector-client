package cn.com.nadav.binance.client.controller.account.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class GetPriceRequest extends AccountInfoRequest {

    private String symbol;

    private List<String> symbols;

}
