package com.sulongfei.jump.rest.request;

import lombok.Data;

import java.sql.Timestamp;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/5/30 15:47
 * @Version 1.0
 */
@Data
public class RegisterRequest {
    private String mobile;

    private Long orgId;

    private Integer saleType;

    private Long saleId;

    private Timestamp registerTime;

    public RegisterRequest(String mobile, Long orgId, Integer saleType, Long saleId, Timestamp registerTime) {
        this.mobile = mobile;
        this.orgId = orgId;
        this.saleType = saleType;
        this.saleId = saleId;
        this.registerTime = registerTime;
    }
}
