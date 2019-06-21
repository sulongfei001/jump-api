package com.sulongfei.jump.rest.request;

import lombok.Data;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/6/20 15:27
 * @Version 1.0
 */
@Data
public class SendGoodsRequest {
    private Integer gameId = 1;

    private Long memberId;
    private Long orgId;
    private Long goodsId;
    private Integer goodsNum;
    private Long saleId;
    private Integer saleType;
    private String sendPerson;
    private String mobile;
    private String sendPlace;
    private Long orderId;

    public SendGoodsRequest(Long memberId, Long orgId, Long goodsId, Integer goodsNum, Long saleId, Integer saleType, String sendPerson, String mobile, String sendPlace, Long orderId) {
        this.memberId = memberId;
        this.orgId = orgId;
        this.goodsId = goodsId;
        this.goodsNum = goodsNum;
        this.saleId = saleId;
        this.saleType = saleType;
        this.sendPerson = sendPerson;
        this.mobile = mobile;
        this.sendPlace = sendPlace;
        this.orderId = orderId;
    }
}
