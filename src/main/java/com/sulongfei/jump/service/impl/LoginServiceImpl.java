package com.sulongfei.jump.service.impl;

import com.sulongfei.jump.constants.Constants;
import com.sulongfei.jump.context.GlobalContext;
import com.sulongfei.jump.dto.UserLoginDTO;
import com.sulongfei.jump.mapper.SecurityUserMapper;
import com.sulongfei.jump.mapper.TicketMapper;
import com.sulongfei.jump.model.SecurityUser;
import com.sulongfei.jump.model.Ticket;
import com.sulongfei.jump.response.Response;
import com.sulongfei.jump.rest.response.RegisterResponse;
import com.sulongfei.jump.rest.response.RestResponse;
import com.sulongfei.jump.service.LoginService;
import com.sulongfei.jump.utils.QCloudUtil;
import com.sulongfei.jump.utils.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/5/29 16:53
 * @Version 1.0
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class LoginServiceImpl implements LoginService {
    @Autowired
    private SecurityUserMapper securityUserMapper;
    @Autowired
    private RedisService redisService;
    @Autowired
    private RestService restService;
    @Autowired
    private GlobalContext globalContext;
    @Autowired
    @Qualifier("taskExecutor")
    private ThreadPoolTaskExecutor taskExecutor;
    @Autowired
    private TicketMapper ticketMapper;

    @Override
    @Transactional(readOnly = false)
    public Response generatingSmsCode(UserLoginDTO dto) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        SecurityUser user = securityUserMapper.selectByUsername(dto.getPhoneNumber());
        String SmsCode = StrUtils.randomNumber(6);
        redisService.set(Constants.RedisName.LOGIN_SMS_CODE + dto.getPhoneNumber(), new BCryptPasswordEncoder().encode(SmsCode), globalContext.getSmsCodeExpire() * 60);
        if (user == null) {
            ResponseEntity<RestResponse<RegisterResponse>> result = restService.register(dto);
            user = new SecurityUser();
            user.setPhoneNumber(dto.getPhoneNumber());
            user.setPassword(new BCryptPasswordEncoder().encode(SmsCode));
            user.setNickname(securityUserMapper.nextKey() + dto.getRemoteClubId() + "");
            user.setGender((byte) 0);
            user.setAvatar(globalContext.getDefaultAvatar());
            user.setCreateTime(now);
            user.setUpdateTime(now);
            user.setRegisterClue(dto.getRemoteClubId());
            user.setDeleteStatus(Constants.Delete.NO);
            user.setLastOperationTime(now);
            user.setLastOperationClub(dto.getRemoteClubId());
            user.setConfirmPush(false);
            if (result.getBody() != null && "200".equals(result.getBody().getErrorCode())) {
                user.setMemberId(result.getBody().getResult().getMemberId());
                user.setIsSaler(result.getBody().getResult().getIsSaler() == 1 ? true : false);
            }
            securityUserMapper.insertSelective(user);
        } else {
            user.setLastOperationTime(now);
            user.setLastOperationClub(dto.getRemoteClubId());
            securityUserMapper.updateByPrimaryKeySelective(user);
        }
        // 门票
        Ticket ticket = ticketMapper.selectByClubId(user.getId(), dto.getRemoteClubId());
        if (ticket == null) {
            ticket = new Ticket(user.getId(), dto.getRemoteClubId(), 0);
            ticketMapper.insertSelective(ticket);
        }
        taskExecutor.execute(() -> QCloudUtil.sendSMS(dto.getPhoneNumber(), SmsCode));
        return new Response();
    }
}
