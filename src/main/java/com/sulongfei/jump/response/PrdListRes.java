package com.sulongfei.jump.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/6/11 10:42
 * @Version 1.0
 */
@ApiModel(value = "卡券列表对象")
@Data
public class PrdListRes {

    @ApiModelProperty(value = "门店兑换")
    private List<PrdRes> exchangeList;

    @ApiModelProperty(value = "寄送卡券")
    private List<PrdRes> exclusiveList;

    public PrdListRes(List<PrdRes> exchangeList, List<PrdRes> exclusiveList) {
        this.exchangeList = exchangeList;
        this.exclusiveList = exclusiveList;
    }
}
