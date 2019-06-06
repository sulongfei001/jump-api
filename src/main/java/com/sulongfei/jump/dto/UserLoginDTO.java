package com.sulongfei.jump.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/5/29 16:55
 * @Version 1.0
 */
@ApiModel(value = "用户登录请求数据")
@Data
public class UserLoginDTO extends BaseDTO {
    @ApiModelProperty(value = "手机号码", required = true)
    private String phoneNumber;
    @ApiModelProperty(value = "注册时间", hidden = true)
    private Long registerTime = System.currentTimeMillis();
}
