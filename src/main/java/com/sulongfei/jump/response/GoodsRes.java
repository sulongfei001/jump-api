package com.sulongfei.jump.response;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/5/30 14:43
 * @Version 1.0
 */
@Data
public class GoodsRes {
    private String goodsName;

    private Integer goodsNum;

    private BigDecimal goodsPrice;

    private String goodsPicture;

    private String goodsText;

    private String picture1;

    private String picture2;

    private String picture3;

    private String picture4;
}
