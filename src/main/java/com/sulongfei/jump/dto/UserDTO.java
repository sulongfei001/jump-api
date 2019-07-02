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
    @ApiModelProperty(value = "用户性别（0：未知、1：男、2：女）")
    private Byte gender;
    @ApiModelProperty(value = "收货姓名")
    private String receiverName;
    @ApiModelProperty(value = "收货地址-省")
    private String province;
    @ApiModelProperty(value = "收货地址-市")
    private String city;
    @ApiModelProperty(value = "收货地址-区")
    private String district;
    @ApiModelProperty(value = "详细地址")
    private String receiverAddress;
}
