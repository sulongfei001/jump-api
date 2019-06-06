package com.sulongfei.jump.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/5/31 15:15
 * @Version 1.0
 */
@ApiModel(value = "排行榜返回对象")
@Data
public class RankListRes {
    @ApiModelProperty(value = "排行榜集合")
    private List<IntegralRes> list;

    @ApiModelProperty(value = "当前用户对象")
    private UserRes user;

    @ApiModelProperty(value = "入榜分数")
    private Integer entryIntegral;
}
