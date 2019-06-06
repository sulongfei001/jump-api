package com.sulongfei.jump.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/5/30 11:49
 * @Version 1.0
 */
@ApiModel(value = "用户信息返回对象")
@Data
public class UserRes {
    @ApiModelProperty(value = "用户ID")
    private Long id;

    @ApiModelProperty(value = "是否为推广员")
    private Boolean isSaler;

    @ApiModelProperty(value = "用户手机号")
    private String phoneNumber;

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

    @ApiModelProperty(value = "每日领取门票")
    private Boolean everydayTicket;
}
