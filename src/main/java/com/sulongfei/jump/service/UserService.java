package com.sulongfei.jump.service;

import com.sulongfei.jump.dto.BaseDTO;
import com.sulongfei.jump.dto.UserDTO;
import com.sulongfei.jump.response.Response;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/5/30 11:45
 * @Version 1.0
 */
public interface UserService {
    Response getUserInfo(BaseDTO dto);

    Response updateUser(UserDTO dto);
}
