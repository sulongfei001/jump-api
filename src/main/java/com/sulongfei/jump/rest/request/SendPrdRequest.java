package com.sulongfei.jump.rest.request;

import lombok.Data;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/5/30 18:06
 * @Version 1.0
 */
@Data
public class SendPrdRequest {
    private Integer gameId = 1;

    private Long memberId;
    private Long orgId;
    private Long goodsId;
    private Integer goodsNum;
    private Long saleId;
    private Integer saleType;

    public SendPrdRequest(Long memberId, Long orgId, Long goodsId, Integer goodsNum, Long saleId, Integer saleType) {
        this.memberId = memberId;
        this.orgId = orgId;
        this.goodsId = goodsId;
        this.goodsNum = goodsNum;
        this.saleId = saleId;
        this.saleType = saleType;
    }
}
