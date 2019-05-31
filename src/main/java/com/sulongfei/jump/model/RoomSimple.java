package com.sulongfei.jump.model;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class RoomSimple extends Model {

    private Long remoteClubId;

    private Timestamp startTime;

    private Timestamp endTime;

    private Boolean hidden;

    private Long remoteGoodsId;

    private String goodsName;

    private Integer goodsNum;

    private BigDecimal goodsPrice;

    private String goodsPicture;

    private String goodsText;

    private Integer ticketNum;

    private BigDecimal prizeProbability;

    private BigDecimal premiumProportion;

    private String picture1;

    private String picture2;

    private String picture3;

    private String picture4;

}