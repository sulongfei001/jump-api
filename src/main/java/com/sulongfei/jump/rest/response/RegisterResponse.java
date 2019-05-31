package com.sulongfei.jump.rest.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/5/30 15:41
 * @Version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RegisterResponse extends RestResponse{
    private Integer isSaler;

    private Long memberId;
}
