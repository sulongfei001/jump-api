package com.sulongfei.jump.service.impl;

import com.sulongfei.jump.config.GlobalValueConfig;
import com.sulongfei.jump.constants.Constants;
import com.sulongfei.jump.dto.UserLoginDTO;
import com.sulongfei.jump.mapper.SecurityUserMapper;
import com.sulongfei.jump.model.SecurityUser;
import com.sulongfei.jump.response.Response;
import com.sulongfei.jump.rest.response.RegisterResponse;
import com.sulongfei.jump.rest.response.RestResponse;
import com.sulongfei.jump.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @Override
    @Transactional(readOnly = false)
    public Response generatingSmsCode(UserLoginDTO dto) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        SecurityUser user = securityUserMapper.selectByUsername(dto.getPhoneNumber());
        // String SmsCode = StrUtils.randomNumber(6);
        String SmsCode = "111111";
        redisService.set(Constants.RedisName.LOGIN_SMS_CODE + dto.getPhoneNumber(), new BCryptPasswordEncoder().encode(SmsCode), GlobalValueConfig.getSmsCodeExpire() * 60);
        if (user == null) {
            ResponseEntity<RestResponse<RegisterResponse>> result = restService.register(dto);
            user = new SecurityUser();
            user.setPhoneNumber(dto.getPhoneNumber());
            user.setPassword(new BCryptPasswordEncoder().encode(SmsCode));
            user.setCreateTime(now);
            user.setUpdateTime(now);
            user.setRegisterClue(dto.getRemoteClubId());
            user.setDeleteStatus(Constants.Delete.NO);
            user.setLastOperationTime(now);
            user.setLastOperationClub(dto.getRemoteClubId());
            user.setEverydayTicket(false);
            user.setTicketNum(0);
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
        return new Response();
    }
}
