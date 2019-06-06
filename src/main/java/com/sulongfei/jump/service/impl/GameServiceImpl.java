package com.sulongfei.jump.service.impl;

import com.sulongfei.jump.dto.BaseDTO;
import com.sulongfei.jump.mapper.RecordMapper;
import com.sulongfei.jump.mapper.SecurityUserMapper;
import com.sulongfei.jump.model.Record;
import com.sulongfei.jump.model.SecurityUser;
import com.sulongfei.jump.response.RandomResultRes;
import com.sulongfei.jump.response.Response;
import com.sulongfei.jump.response.UserRes;
import com.sulongfei.jump.service.GameService;
import com.sulongfei.jump.web.interceptor.UserInterceptor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/6/6 16:30
 * @Version 1.0
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class GameServiceImpl implements GameService {

    @Autowired
    private SecurityUserMapper userMapper;
    @Autowired
    private RecordMapper recordMapper;

    @Override
    public Response randomGameResult(BaseDTO dto) {
        Record record = recordMapper.randomResult(dto.getRemoteClubId(), UserInterceptor.getLocalUser().getId());
        if (record == null) {
            return new Response();
        }
        SecurityUser user = userMapper.selectByPrimaryKey(record.getUserId());
        UserRes userRes = new UserRes();
        BeanUtils.copyProperties(user, userRes);
        RandomResultRes data = new RandomResultRes(record.getIntegral(), userRes);
        return new Response(data);
    }
}
