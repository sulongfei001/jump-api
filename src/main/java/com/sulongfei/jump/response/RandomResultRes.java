package com.sulongfei.jump.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/6/6 17:33
 * @Version 1.0
 */
@ApiModel(value = "随机游戏结果返回对象")
@Data
public class RandomResultRes {
    @ApiModelProperty(value = "随机用户分数")
    private Integer integral;

    @ApiModelProperty(value = "随机用户对象")
    private UserRes user;

    @ApiModelProperty(value = "本用户排名")
    private Integer ownRank;

    @ApiModelProperty(value = "对手用户排名(-1 未入榜)")
    private Integer rivalRank;

}
