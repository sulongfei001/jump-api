package com.sulongfei.jump.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

@EqualsAndHashCode(callSuper = true)
@Data
public class RoomSpread extends Model {
    private Long id;

    private Long remoteClubId;

    private String password;

    private Long saleId;

    private Integer saleType;

    private Timestamp createTime;

    private Long spreadGoodsId;

    private Integer ticketNum;

    private Integer joinNum;

    private Integer partakeNum;

    private Long prizeUserId;

    private SpreadGoods spreadGoods;

    private SecurityUser user;
}