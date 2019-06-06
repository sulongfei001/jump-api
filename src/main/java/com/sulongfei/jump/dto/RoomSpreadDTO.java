package com.sulongfei.jump.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/6/3 14:48
 * @Version 1.0
 */
@ApiModel(value = "推广员创建房间请求数据")
@EqualsAndHashCode(callSuper = true)
@Data
public class RoomSpreadDTO extends BaseDTO {
    @ApiModelProperty(value = "推广商品ID", example = "0")
    private Long spreadGoodsId;
    @ApiModelProperty(value = "参与人数", example = "0")
    private Integer joinNum;
    @ApiModelProperty(value = "每次消耗门票数量", example = "0")
    private Integer ticketNum;

}
