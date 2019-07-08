package com.sulongfei.jump.service.impl;

import com.sulongfei.jump.context.GlobalContext;
import com.sulongfei.jump.dto.BaseDTO;
import com.sulongfei.jump.dto.UserDTO;
import com.sulongfei.jump.mapper.SecurityUserMapper;
import com.sulongfei.jump.mapper.TicketMapper;
import com.sulongfei.jump.model.SecurityUser;
import com.sulongfei.jump.model.Ticket;
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
    @Autowired
    private TicketMapper ticketMapper;

    @Override
    public Response getUserInfo(BaseDTO dto) {
        SecurityUser user = UserInterceptor.getLocalUser();
        Ticket ticket = ticketMapper.selectByClubId(user.getId(), dto.getRemoteClubId());
        UserRes data = new UserRes();
        BeanUtils.copyProperties(user, data);
        data.setTicketNum(ticket.getNum());
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
