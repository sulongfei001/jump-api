package com.sulongfei.jump.rest.response;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/6/20 16:00
 * @Version 1.0
 */
@Data
public class MarketOrderResponse {
    private Long sendOrderId;
    private Long createTime;
    private Long status;
    private String name;
    private BigDecimal price;
    private Integer goodNum;
    private String sendNo;
    private String supplierName;
    private String saleInfo;
    private String logo;
}
