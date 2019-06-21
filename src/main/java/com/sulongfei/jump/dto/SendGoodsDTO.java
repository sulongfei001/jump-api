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
    @ApiModelProperty(value = "寄送地址")
    private String sendPlace;
}
