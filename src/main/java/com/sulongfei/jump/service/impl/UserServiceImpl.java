package com.sulongfei.jump.service.impl;

import cn.hutool.core.util.StrUtil;
import com.sulongfei.jump.context.GlobalContext;
import com.sulongfei.jump.dto.UserDTO;
import com.sulongfei.jump.mapper.SecurityUserMapper;
import com.sulongfei.jump.model.SecurityUser;
import com.sulongfei.jump.response.Response;
import com.sulongfei.jump.response.UserRes;
import com.sulongfei.jump.service.UserService;
import com.sulongfei.jump.web.interceptor.UserInterceptor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.List;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/5/30 11:46
 * @Version 1.0
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class UserServiceImpl implements UserService {
    @Autowired
    private SecurityUserMapper userMapper;
    @Autowired
    private GlobalContext context;

    @Override
    public Response getUserInfo() {
        SecurityUser user = UserInterceptor.getLocalUser();
        UserRes data = new UserRes();
        BeanUtils.copyProperties(user, data);
        return new Response(data);
    }

    @Override
    @Transactional(readOnly = false)
    public Response updateUser(UserDTO dto) {
        SecurityUser user = UserInterceptor.getLocalUser();
        BeanUtils.copyProperties(dto, user);
        user.setAvatar(ObjectUtils.isEmpty(dto.getAvatar()) ? context.getDefaultAvatar() : dto.getAvatar());
        userMapper.updateByPrimaryKeySelective(user);
        return new Response();
    }
}
