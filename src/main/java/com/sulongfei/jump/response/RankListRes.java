package com.sulongfei.jump.response;

import lombok.Data;

import java.util.List;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/5/31 15:15
 * @Version 1.0
 */
@Data
public class RankListRes {
    private List<IntegralRes> list;
    private UserRes user;
    private Integer entryIntegral;
}
