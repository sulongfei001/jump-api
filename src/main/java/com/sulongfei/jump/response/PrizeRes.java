package com.sulongfei.jump.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/6/10 16:11
 * @Version 1.0
 */
@ApiModel(value = "排行榜奖励对象")
@Data
public class PrizeRes {
    @ApiModelProperty(value = "商品ID")
    private Long remoteGoodsId;

    @ApiModelProperty(value = "商品个数")
    private Integer goodsNum;

    @ApiModelProperty(value = "商品图片")
    private String goodsPicture;
}
