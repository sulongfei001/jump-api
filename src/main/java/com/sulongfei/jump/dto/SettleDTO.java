package com.sulongfei.jump.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
    @ApiModelProperty(value = "分数", example = "0")
    private Integer integral;
    @ApiModelProperty(value = "卡券ID", example = "0")
    private Long cardId;
    @ApiModelProperty(value = "卡券数量", example = "0")
    private Integer cardNum;
    @ApiModelProperty(value = "奖励门票数量", example = "0")
    private Integer getTicket;

}
