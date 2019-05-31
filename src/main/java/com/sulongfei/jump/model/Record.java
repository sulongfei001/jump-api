package com.sulongfei.jump.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

@EqualsAndHashCode(callSuper = true)
@Data
public class Record extends Model {
    private Long id;

    private Long userId;

    private Long remoteClubId;

    private Integer consumeTicket;

    private Long saleId;

    private Integer saleType;

    private Timestamp createTime;

    public Record(Long userId, Long remoteClubId, Integer consumeTicket, Long saleId, Integer saleType, Timestamp createTime) {
        this.userId = userId;
        this.remoteClubId = remoteClubId;
        this.consumeTicket = consumeTicket;
        this.saleId = saleId;
        this.saleType = saleType;
        this.createTime = createTime;
    }
}