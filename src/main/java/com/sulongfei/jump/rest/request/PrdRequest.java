package com.sulongfei.jump.rest.request;

import lombok.Data;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/6/11 11:17
 * @Version 1.0
 */
@Data
public class PrdRequest {
    private Long memberId;
    private Long orgId;
    private Long gameId;

    public PrdRequest(Long memberId, Long orgId, Long gameId) {
        this.memberId = memberId;
        this.orgId = orgId;
        this.gameId = gameId;
    }
}
