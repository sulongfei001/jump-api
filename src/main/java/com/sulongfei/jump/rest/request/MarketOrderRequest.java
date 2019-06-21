package com.sulongfei.jump.rest.request;

import lombok.Data;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/6/20 16:04
 * @Version 1.0
 */
@Data
public class MarketOrderRequest {
    private Long memberId;
    private Integer status;
    private Long orgId;

    public MarketOrderRequest(Long memberId, Long orgId) {
        this.memberId = memberId;
        this.orgId = orgId;
    }
}
