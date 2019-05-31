package com.sulongfei.jump.dto;

import lombok.Data;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/5/30 16:34
 * @Version 1.0
 */
@Data
public class SettleDTO extends BaseDTO {

    private Integer integral;

    private Long goodsId;

    private Integer goodsNum;

    private Long cardId;

    private Integer cardNum;

    private Integer consumeTicket;

    private Integer getTicket;

}
