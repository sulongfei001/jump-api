package com.sulongfei.jump.web.interceptor;

import com.sulongfei.jump.constants.ResponseStatus;
import com.sulongfei.jump.exception.JumpException;
import com.sulongfei.jump.mapper.SecurityUserMapper;
import com.sulongfei.jump.model.SecurityUser;
import com.sulongfei.jump.utils.PrincipalUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @Description
 * @Author sulongfei
 * @Date 2018/12/24 11:46
 * @Version 1.0
 */
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
@Slf4j
public class UserInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private SecurityUserMapper userMapper;

    private static ThreadLocal<SecurityUser> localUser = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        SecurityUser sysUser = userMapper.selectByUsername((PrincipalUtils.getPrincipal().getName()));
        if (sysUser == null) {
            throw new JumpException(ResponseStatus.USER_PHONE_NOT_EXISTS);
        }
        if (sysUser.getDeleteStatus()) {
            throw new JumpException(ResponseStatus.Code.ACCOUNT_EXCEPTION, ResponseStatus.ACCOUNT_EXCEPTION);
        }
        localUser.set(sysUser);

        return true;
    }

    public static SecurityUser getLocalUser() {
        return localUser.get();
    }
}
