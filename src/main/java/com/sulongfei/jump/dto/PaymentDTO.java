package com.sulongfei.jump.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/6/21 10:56
 * @Version 1.0
 */
@ApiModel(value = "支付结果对象")
@EqualsAndHashCode(callSuper = true)
@Data
public class PaymentDTO extends BaseDTO {

    @ApiModelProperty(value = "产品ID")
    private Long productId;

    @ApiModelProperty(value = "产品数量")
    private Integer num;

    @ApiModelProperty(value = "订单总价")
    private BigDecimal price;

    @ApiModelProperty(value = "奖励门票数量")
    private Integer ticketNum;

    @ApiModelProperty(value = "订单时间")
    private Long buyTime;

    @ApiModelProperty(value = "订单号")
    private Long swOrderId;

    @ApiModelProperty(value = "卡券发放状态")
    private Byte status;
}
