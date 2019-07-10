package com.sulongfei.jump.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/7/10 17:23
 * @Version 1.0
 */
@ApiModel(value = "房间返回对象")
@Data
public class RoomRes {
    @ApiModelProperty(value = "房间ID")
    private Long id;
    @ApiModelProperty(value = "门票个数")
    private Integer ticketNum;
    @ApiModelProperty(value = "商品名字")
    private String goodsName;
    @ApiModelProperty(value = "随机宝石格子")
    private List<Integer> randomCells;
    @ApiModelProperty(value = "允许参与人数")
    private Integer joinNum;
    @ApiModelProperty(value = "已经参与人数")
    private Integer partakeNum;
    @ApiModelProperty(value = "本次游戏记录ID")
    private Long recordId;
    @ApiModelProperty(value = "用户游戏前排名")
    private Integer formerRank;
}
