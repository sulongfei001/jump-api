package com.sulongfei.jump.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/6/4 14:46
 * @Version 1.0
 */
@ApiModel(value = "推广员创建房间返回对象")
@Data
public class RoomSpreadRes {

    @ApiModelProperty(value = "房间ID")
    private Long id;
    @ApiModelProperty(value = "房间口令")
    private String password;
    @ApiModelProperty(value = "创建时间")
    private Long createTime;
    @ApiModelProperty(value = "最后更新时间")
    private Long lastUpdateTime;
    @ApiModelProperty(value = "单次门票消耗")
    private Integer ticketNum;
    @ApiModelProperty(value = "允许参与人数")
    private Integer joinNum;
    @ApiModelProperty(value = "已经参与人数")
    private Integer partakeNum;
    @ApiModelProperty(value = "房间是否结束")
    private Boolean ended;
    @ApiModelProperty(value = "房间对应的商品对象")
    private SpreadGoodsRes spreadGoods;
    @ApiModelProperty(value = "房间对应中将人对象")
    private UserRes user;
}
