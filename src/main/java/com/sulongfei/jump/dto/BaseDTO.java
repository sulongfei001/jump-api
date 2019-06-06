package com.sulongfei.jump.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/5/29 17:06
 * @Version 1.0
 */
@ApiModel(value = "基础请求数据")
@Data
public class BaseDTO {
    @ApiModelProperty(hidden = true)
    private Long remoteClubId;
    @ApiModelProperty(hidden = true)
    private Integer saleType;
    @ApiModelProperty(hidden = true)
    private Long saleId;

}
