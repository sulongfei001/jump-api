package com.sulongfei.jump.service;

import com.sulongfei.jump.dto.BaseDTO;
import com.sulongfei.jump.response.Response;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/6/6 16:29
 * @Version 1.0
 */
public interface GameService {
    Response randomGameResult(BaseDTO dto);
}
