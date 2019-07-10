package com.sulongfei.jump.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/5/30 16:34
 * @Version 1.0
 */
@ApiModel(value = "游戏结算请求数据")
@EqualsAndHashCode(callSuper = true)
@Data
public class SettleDTO extends BaseDTO {

    @ApiModelProperty(value = "房间ID", example = "0")
    private Long roomId;
    /*@ApiModelProperty(value = "分数", example = "0")
    private Integer integral;*/
    /*@ApiModelProperty(value = "奖励门票数量", example = "0")
    private Integer getTicket;*/
    @ApiModelProperty(value = "用户通过格子数量",example = "0")
    private Integer passCellNum;
    @ApiModelProperty(value = "随机宝石格子")
    private List<Integer> randomCells;
    @ApiModelProperty(value = "胜利或失败")
    private Boolean isWin;
    @ApiModelProperty(value = "本次游戏记录ID")
    private Long recordId;
}
