package com.sulongfei.jump.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/6/20 15:21
 * @Version 1.0
 */
@ApiModel(value = "商品寄送请求数据")
@EqualsAndHashCode(callSuper = true)
@Data
public class SendGoodsDTO extends BaseDTO{

    @ApiModelProperty(value = "商品寄送ID")
    private Long id;

    @ApiModelProperty(value = "收货人姓名")
    private String sendPerson;

    @ApiModelProperty(value = "收货地址-省")
    private String province;

    @ApiModelProperty(value = "收货地址-市")
    private String city;

    @ApiModelProperty(value = "收货地址-区")
    private String district;

    @ApiModelProperty(value = "详细地址")
    private String receiverAddress;
}
