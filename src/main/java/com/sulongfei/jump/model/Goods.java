package com.sulongfei.jump.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
public class Goods extends Model {
    private Long id;

    private Long remoteGoodsId;

    private Long remoteClubId;

    private String goodsName;

    private Integer goodsNum;

    private BigDecimal goodsPrice;

    private Integer goodsType;

    private String goodsPicture;

    private String detailPicture;

    private Integer remainNum;

    private String goodsText;
}