package com.sulongfei.jump.service;

import com.sulongfei.jump.dto.BaseDTO;
import com.sulongfei.jump.response.Response;

import java.io.IOException;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/6/12 17:11
 * @Version 1.0
 */
public interface ChargeService {
    Response chargeList(BaseDTO dto) throws IOException;
}
