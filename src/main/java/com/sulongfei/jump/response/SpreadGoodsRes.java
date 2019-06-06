package com.sulongfei.jump.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/5/27 11:55
 * @Version 1.0
 */
@ApiModel(value = "推广员商品返回对象")
@Data
public class SpreadGoodsRes implements Serializable {

    private static final long serialVersionUID = -4003437270082575448L;

    @ApiModelProperty(value = "推广员商品ID")
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

    @ApiModelProperty(value = "商品可用库存")
    private Integer remainNum;

    @ApiModelProperty(value = "图片1")
    private String picture1;

    @ApiModelProperty(value = "图片2")
    private String picture2;

    @ApiModelProperty(value = "图片3")
    private String picture3;

    @ApiModelProperty(value = "图片4")
    private String picture4;


}
