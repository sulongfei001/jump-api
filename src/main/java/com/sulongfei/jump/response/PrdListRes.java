package com.sulongfei.jump.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/6/11 10:42
 * @Version 1.0
 */
@ApiModel(value = "卡券列表对象")
@Data
public class PrdListRes {
    @ApiModelProperty(value = "门店兑换")
    private List<PrdRes> exclusiveList;

    @ApiModelProperty(value = "未寄送商品")
    private List<SendGoodsRes> goodsList;

    @ApiModelProperty(value = "寄送商品")
    private List<MarketOrderRes> marketOrderList;

    public PrdListRes(List<PrdRes> exclusiveList, List<SendGoodsRes> goodsList, List<MarketOrderRes> marketOrderList) {
        this.exclusiveList = exclusiveList;
        this.goodsList = goodsList;
        this.marketOrderList = marketOrderList;
    }
}
