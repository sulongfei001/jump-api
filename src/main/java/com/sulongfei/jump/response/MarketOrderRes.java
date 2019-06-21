package com.sulongfei.jump.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/6/20 16:09
 * @Version 1.0
 */
@ApiModel(value = "奖品兑换已寄送")
@Data
public class MarketOrderRes {
    @ApiModelProperty(value = "订单ID")
    private Long sendOrderId;
    @ApiModelProperty(value = "订单创建时间")
    private Long createTime;
    @ApiModelProperty(value = "状态（0代发货，1 已发货，3已退款）")
    private Long status;
    @ApiModelProperty(value = "商品信息")
    private String name;
    @ApiModelProperty(value = "订单价格")
    private BigDecimal price;
    @ApiModelProperty(value = "数量")
    private Integer goodNum;
    @ApiModelProperty(value = "快递单号")
    private String sendNo;
    @ApiModelProperty(value = "供应商名")
    private String supplierName;
    @ApiModelProperty(value = "客服电话")
    private String saleInfo;
    @ApiModelProperty(value = "商品图片")
    private String logo;
}
