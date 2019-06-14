package com.sulongfei.jump.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/6/11 10:44
 * @Version 1.0
 */
@ApiModel(value = "卡券对象")
@Data
public class PrdRes {
    @ApiModelProperty(value = "券码")
    private String prdNo;

    @ApiModelProperty(value = "状态(1、未使用；2、已锁定；3、已使用；4、已过期)")
    private Long status;

    /*@ApiModelProperty(value = "获取场所ID")
    private Long receivePlace;*/

    @ApiModelProperty(value = "获取时间")
    private Long receiveTime;

    @ApiModelProperty(value = "失效时间，没有则是不失效")
    private Long beginTime;

    @ApiModelProperty(value = "商品名称")
    private String name;

    @ApiModelProperty(value = "商品上架标题")
    private String title;

    @ApiModelProperty(value = "卡券使用场所")
    private String usePlace;
}
