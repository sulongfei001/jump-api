package com.sulongfei.jump.rest.response;

import lombok.Data;

import java.util.Date;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/6/11 11:19
 * @Version 1.0
 */
@Data
public class PrdResponse {
    private Long goodsId;

    private String prdNo;

    private Long status;

    private Long receivePlace;

    private Long receiveTime;

    private Long beginTime;

    private Long useType;

    private String name;

    private String title;

    private String logo;
}
