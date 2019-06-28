package com.sulongfei.jump.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/6/12 19:08
 * @Version 1.0
 */
@ApiModel(value = "内购对象")
@Data
public class ChargeListRes {
    @ApiModelProperty(value = "唯一标识ID")
    private Long id;

    @ApiModelProperty(value = "归属门店ID", hidden = true)
    private Long remoteClubId;

    @ApiModelProperty(value = "备注（无用项）", hidden = true)
    private String mark;

    @ApiModelProperty(value = "充值名称")
    private String chargeName;

    @ApiModelProperty(value = "商品道具", hidden = true)
    private Long goodsId;

    @ApiModelProperty(value = "商品数量", hidden = true)
    private Integer goodsNum;

    @ApiModelProperty(value = "商品图片")
    private String goodsPicture;

    @ApiModelProperty(value = "商品详情")
    private String goodsText;

    @ApiModelProperty(value = "价格")
    private BigDecimal price;

    @ApiModelProperty(value = "副标题")
    private String ticketTitle;

    @ApiModelProperty(value = "游戏门票数量", hidden = true)
    private Integer ticketNum;
}
