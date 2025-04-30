package cn.com.nadav.binance.client.controller.excel;

import cn.com.nadav.binance.client.controller.account.request.GetAccountRequest;
import cn.com.nadav.binance.client.controller.account.request.GetPriceRequest;
import cn.com.nadav.binance.client.service.SpotRestApiCacheService;
import cn.hutool.core.date.DateUtil;
import com.alibaba.excel.EasyExcel;
import com.binance.connector.client.spot.rest.model.GetAccountResponse;
import com.binance.connector.client.spot.rest.model.GetAccountResponseBalancesInner;
import com.binance.connector.client.spot.rest.model.TickerPriceResponse;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.compress.utils.Sets;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/export")
public class ExcelExportController {


    private SpotRestApiCacheService spotRestApiCacheService;

    public ExcelExportController(SpotRestApiCacheService spotRestApiCacheService) {
        this.spotRestApiCacheService = spotRestApiCacheService;
    }


    @GetMapping("/user/balance")
    public void exportExcel(GetAccountRequest getAccountRequest, HttpServletResponse response) throws Exception {
        // 构造导出数据
        GetAccountResponse getAccountResponse = spotRestApiCacheService.queryAccountDetailsInfo(getAccountRequest);


        List<@Valid GetAccountResponseBalancesInner> balances = getAccountResponse.getBalances();
        if (balances == null) {
            return;
        }

        Set<String> assets = Sets.newHashSet();
//        balances.forEach(balance -> {
//            String asset = balance.getAsset();
//            assets.add(asset);
//        });

        assets.add(balances.get(0).getAsset());

        GetPriceRequest getPriceRequest = new GetPriceRequest();
        BeanUtils.copyProperties(getAccountRequest, getPriceRequest);
        getPriceRequest.setSymbols(new ArrayList<>(assets));
        TickerPriceResponse currentPriceResponse = spotRestApiCacheService.queryCurrentPrice(getPriceRequest);


        // 日期字符串
        // 当前时间格式化为 yyyy-MM-dd
        String dateStr = DateUtil.formatDateTime(new Date());
        String rawFileName = "用户资金信息_" + dateStr;

        String fileName = URLEncoder.encode(rawFileName, "UTF-8").replaceAll("\\+", "%20");

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");


        List<UserBalanceExcelDTO> dataList = Lists.newArrayList();
        balances.forEach(balance ->

                {
                    UserBalanceExcelDTO userBalanceExcelDTO = new UserBalanceExcelDTO();
                    userBalanceExcelDTO.setAsset(balance.getAsset());
                    userBalanceExcelDTO.setFree(balance.getFree());
                    userBalanceExcelDTO.setLocked(balance.getLocked());

                    BigDecimal total = BigDecimal.ZERO;
                    if (StringUtils.isNotEmpty(balance.getFree())) {
                        total = total.add(new BigDecimal(balance.getFree()));
                    }
                    if (StringUtils.isNotEmpty(balance.getLocked())) {
                        total = total.add(new BigDecimal(balance.getLocked()));
                    }

                    userBalanceExcelDTO.setTotal(total.toPlainString());

                    dataList.add(userBalanceExcelDTO);
                }
        );

        // 写入 Excel 到 response 输出流
        EasyExcel.write(response.getOutputStream(), UserBalanceExcelDTO.class)
                .sheet("用户资金信息")
                .doWrite(dataList);
    }
}

