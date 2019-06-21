package com.sulongfei.jump.response;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/6/20 16:33
 * @Version 1.0
 */
@Data
public class SendGoodsRes {
    private Long id;

    private String goodsName;

    private Integer goodsNum;

    private BigDecimal goodsPrice;

    private String goodsPicture;

}
