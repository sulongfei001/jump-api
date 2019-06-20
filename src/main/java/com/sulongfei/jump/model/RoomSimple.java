package com.sulongfei.jump.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.sql.Timestamp;

@EqualsAndHashCode(callSuper = true)
@Data
public class RoomSimple extends Model {
    private Long id;

    private Long remoteClubId;

    private Timestamp startTime;

    private Timestamp endTime;

    private Timestamp createTime;

    private Boolean hidden;

    private Long remoteGoodsId;

    private String goodsName;

    private Integer goodsNum;

    private String goodsPicture;

    private String goodsText;

    private Integer ticketNum;

    private Integer prizeProbability;

    private Integer premiumProportion;

    private Integer consumeNum;

    private String picture1;

    private String picture2;

    private String picture3;

    private String picture4;

}