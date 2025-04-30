package cn.com.nadav.binance.client.controller.excel.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class UserBalanceExcelDTO {


    @ExcelProperty("货币名称")
    private String asset;

    @ExcelProperty("货币当前价格")
    private String balance;

    @ExcelProperty("活期货币数量")
    private String free;

    @ExcelProperty("活期货币余额")
    private String freeBalance;

    @ExcelProperty("定存货币数量")
    private String locked;

    @ExcelProperty("活期货币余额")
    private String lockedBalance;

    @ExcelProperty("货币总数量")
    private String total;

    @ExcelProperty("货币总余额")
    private String totalBalance;

}
