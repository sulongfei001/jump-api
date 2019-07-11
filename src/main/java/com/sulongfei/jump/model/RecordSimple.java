package com.sulongfei.jump.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

@EqualsAndHashCode(callSuper = true)
@Data
public class RecordSimple extends Model {
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

    private Boolean settled;

    public RecordSimple() {
    }

    public RecordSimple(Long userId, Long roomId, Integer integral, Boolean isWin, Integer consumeTicket, Integer getTicket, Long saleId, Integer saleType, Timestamp createTime, Boolean settled) {
        this.userId = userId;
        this.roomId = roomId;
        this.integral = integral;
        this.isWin = isWin;
        this.consumeTicket = consumeTicket;
        this.getTicket = getTicket;
        this.saleId = saleId;
        this.saleType = saleType;
        this.createTime = createTime;
        this.settled = settled;
    }
}