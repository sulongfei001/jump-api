package com.sulongfei.jump.dto;

import lombok.Data;

import java.sql.Timestamp;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/5/29 16:55
 * @Version 1.0
 */
@Data
public class UserLoginDTO extends BaseDTO{
    private String phoneNumber;

    private Timestamp registerTime;
}
