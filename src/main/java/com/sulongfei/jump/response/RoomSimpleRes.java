package com.sulongfei.jump.response;

import com.sulongfei.jump.model.Integral;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/5/30 14:41
 * @Version 1.0
 */
@ApiModel(value = "游戏房间返回对象")
@Data
public class RoomSimpleRes {
    @ApiModelProperty(value = "房间ID")
    private Long id;

    @ApiModelProperty(value = "商品名字")
    private String goodsName;

    @ApiModelProperty(value = "商品个数")
    private Integer goodsNum;

    @ApiModelProperty(value = "商品价格")
    private BigDecimal goodsPrice;

    @ApiModelProperty(value = "商品图片")
    private String goodsPicture;

    @ApiModelProperty(value = "商品说明")
    private String goodsText;

    @ApiModelProperty(value = "门票个数")
    private Integer ticketNum;

    @ApiModelProperty(value = "图片1")
    private String picture1;

    @ApiModelProperty(value = "图片2")
    private String picture2;

    @ApiModelProperty(value = "图片3")
    private String picture3;

    @ApiModelProperty(value = "图片4")
    private String picture4;

    @ApiModelProperty(value = "随机宝石格子")
    private List<Integer> randomCells;
}
