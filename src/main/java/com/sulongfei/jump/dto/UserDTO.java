package com.sulongfei.jump.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/5/30 13:44
 * @Version 1.0
 */
@ApiModel(value = "用户信息请求数据")
@EqualsAndHashCode(callSuper = true)
@Data
public class UserDTO extends BaseDTO {

    @ApiModelProperty(value = "用户头像")
    private String avatar;
    @ApiModelProperty(value = "用户昵称")
    private String nickname;
    @ApiModelProperty(value = "用户性别")
    private Byte gender;
    @ApiModelProperty(value = "收货姓名")
    private String receiverName;
    @ApiModelProperty(value = "收货地址")
    private String receiverAddress;
}
