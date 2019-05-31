package com.sulongfei.jump.service;

import com.sulongfei.jump.dto.UserLoginDTO;
import com.sulongfei.jump.response.Response;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/5/29 16:53
 * @Version 1.0
 */
public interface LoginService {
    Response generatingSmsCode(UserLoginDTO dto);
}
