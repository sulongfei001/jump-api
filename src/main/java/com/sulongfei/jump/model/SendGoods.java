package com.sulongfei.jump.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

@EqualsAndHashCode(callSuper = true)
@Data
public class SendGoods extends Model {
    private Long id;

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

    private Timestamp createTime;

    private Byte status;

    public SendGoods() {
    }

    public SendGoods(Long memberId, Long orgId, Long goodsId, Integer goodsNum, Long saleId, Integer saleType, Long orderId, Timestamp createTime, Byte status) {
        this.memberId = memberId;
        this.orgId = orgId;
        this.goodsId = goodsId;
        this.goodsNum = goodsNum;
        this.saleId = saleId;
        this.saleType = saleType;
        this.orderId = orderId;
        this.createTime = createTime;
        this.status = status;
    }
}