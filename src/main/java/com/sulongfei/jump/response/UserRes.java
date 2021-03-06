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

    @ApiModelProperty(value = "memberId")
    private Long memberId;

    @ApiModelProperty(value = "是否为推广员")
    private Boolean isSaler;

    @ApiModelProperty(value = "用户手机号")
    private String phoneNumber;

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

    @ApiModelProperty(value = "消息推送确认")
    private Boolean confirmPush;

    @ApiModelProperty(value = "用户所有门票")
    private Integer ticketNum;
}
