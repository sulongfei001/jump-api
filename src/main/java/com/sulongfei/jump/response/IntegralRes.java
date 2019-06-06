package com.sulongfei.jump.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/5/31 15:17
 * @Version 1.0
 */
@ApiModel(value = "排行榜单条记录对象")
@Data
public class IntegralRes {
    @ApiModelProperty(value = "用户分数")
    private Integer integral;
    @ApiModelProperty(value = "用户对象")
    private UserRes user;
}
