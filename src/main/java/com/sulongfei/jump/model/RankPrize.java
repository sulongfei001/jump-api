package com.sulongfei.jump.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RankPrize extends Model {

    private Long id;

    private Long remoteClubId;

    private Integer sequence;

    private Long remoteGoodsId;

    private Integer goodsNum;

    private Goods goods;

}