package com.sulongfei.jump.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/5/29 16:55
 * @Version 1.0
 */
@ApiModel
@Data
public class UserLoginDTO extends BaseDTO {
    @ApiModelProperty
    private String phoneNumber;
    @ApiModelProperty(hidden = true)
    private Timestamp registerTime;
}
