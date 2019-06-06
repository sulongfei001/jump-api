package com.sulongfei.jump.service.impl;

import com.sulongfei.jump.constants.Constants;
import com.sulongfei.jump.constants.ResponseStatus;
import com.sulongfei.jump.exception.JumpException;
import com.sulongfei.jump.mapper.SecurityUserMapper;
import com.sulongfei.jump.model.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;


@Service
@Primary
public class JdbcUserDetailsService implements UserDetailsService {

    @Autowired
    private SecurityUserMapper sysUserMapper;
    @Autowired
    private RedisService redisService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        SecurityUser user = sysUserMapper.selectByUsername(userName);
        String password = redisService.get(Constants.RedisName.LOGIN_SMS_CODE + userName);
        if (null == user) {
            throw new JumpException(ResponseStatus.USER_PHONE_NOT_EXISTS);
        }
        if (StringUtils.isEmpty(password)) {
            throw new JumpException(ResponseStatus.SMS_CODE_EXPIRE);
        }
        List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
        return new User(userName, password, grantedAuthorities);
    }


}
