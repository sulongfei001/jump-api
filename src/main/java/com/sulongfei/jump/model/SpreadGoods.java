package com.sulongfei.jump.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
public class SpreadGoods extends Model {
    private Long id;

    private Long remoteClubId;

    private Long remoteGoodsId;

    private String goodsName;

    private Integer goodsNum;

    private String goodsPicture;

    private String goodsText;

    private Integer premiumProportion;

    private String picture1;

    private String picture2;

    private String picture3;

    private String picture4;

    private Goods goods;

}