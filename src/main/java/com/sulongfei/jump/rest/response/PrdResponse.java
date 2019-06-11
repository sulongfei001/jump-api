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
    private String prdNo;

    private Long status;

    private Long receivePlace;

    private Date receiveTime;

    private Date beginTime;

    private Long useType;

    private String name;

    private String title;
}
