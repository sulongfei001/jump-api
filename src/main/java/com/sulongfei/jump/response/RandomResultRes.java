package com.sulongfei.jump.response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/6/6 17:33
 * @Version 1.0
 */
@Api(value = "随机游戏结果返回对象")
@Data
public class RandomResultRes {
    @ApiModelProperty(value = "随机用户分数")
    private Integer integral;

    @ApiModelProperty(value = "随机用户对象")
    private UserRes user;

    public RandomResultRes(Integer integral, UserRes user) {
        this.integral = integral;
        this.user = user;
    }
}
