package com.sulongfei.jump.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/5/30 13:44
 * @Version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserDTO extends BaseDTO {
    private String avatar;

    private String nickname;

    private Byte gender;

    private String receiverName;

    private String receiverAddress;

    private Boolean everydayTicket;
}
