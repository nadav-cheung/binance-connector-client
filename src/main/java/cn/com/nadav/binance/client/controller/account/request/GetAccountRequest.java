package cn.com.nadav.binance.client.controller.account.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GetAccountRequest extends AccountInfoRequest {

    private Boolean omitZeroBalances;

    private Long receiveWindow;

}
