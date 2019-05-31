package com.sulongfei.jump.rest.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/5/30 18:00
 * @Version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SendPrdResponse extends RestResponse {
    private String result;

    private Long sendTime;
}
