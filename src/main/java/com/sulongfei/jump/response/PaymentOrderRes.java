package com.sulongfei.jump.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/6/21 16:22
 * @Version 1.0
 */
@ApiModel(value = "订单")
@Data
public class PaymentOrderRes {

    @ApiModelProperty(value = "商品名称")
    private String name;

    @ApiModelProperty(value = "商品数量")
    private Integer productNum;

    @ApiModelProperty(value = "商品总价")
    private BigDecimal price;

    @ApiModelProperty(value = "订单时间")
    private Long buyTime;

    @ApiModelProperty(value = "订单号")
    private Long swOrderId;

    @ApiModelProperty(value = "订单状态")
    private Byte result;

    @ApiModelProperty(value = "卡券发放状态")
    private Byte status;

    @ApiModelProperty(value = "商品图片")
    private String logo;

}
