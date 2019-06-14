package com.sulongfei.jump.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

@EqualsAndHashCode(callSuper = true)
@Data
public class RecordSpread extends Model {
    private Long id;

    private Long userId;

    private Long roomId;

    private Integer integral;

    private Boolean isWin;

    private Integer consumeTicket;

    private Integer getTicket;

    private Long saleId;

    private Integer saleType;

    private Timestamp createTime;

    public RecordSpread() {
    }

    public RecordSpread(Long userId, Long roomId, Integer integral, Boolean isWin, Integer consumeTicket, Integer getTicket, Long saleId, Integer saleType, Timestamp createTime) {
        this.userId = userId;
        this.roomId = roomId;
        this.integral = integral;
        this.isWin = isWin;
        this.consumeTicket = consumeTicket;
        this.getTicket = getTicket;
        this.saleId = saleId;
        this.saleType = saleType;
        this.createTime = createTime;
    }
}