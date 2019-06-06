package com.sulongfei.jump.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/6/4 13:57
 * @Version 1.0
 */
@ApiModel(value = "游戏结算结果")
@Data
public class SettleRes {
    @ApiModelProperty(value = "用户当前总分数")
    private Integer countIntegral;
    @ApiModelProperty(value = "用户排名上升")
    private Integer rankUp;
    @ApiModelProperty(value = "用户是否中奖")
    private Boolean win;

    public SettleRes(Integer countIntegral, Integer rankUp, Boolean win) {
        this.countIntegral = countIntegral;
        this.rankUp = rankUp;
        this.win = win;
    }
}