package com.sulongfei.jump.service.impl;

import com.sulongfei.jump.constants.Constants;
import com.sulongfei.jump.context.GlobalContext;
import com.sulongfei.jump.mapper.IntegralMapper;
import com.sulongfei.jump.mapper.SendGoodsMapper;
import com.sulongfei.jump.model.Integral;
import com.sulongfei.jump.model.SendGoods;
import com.sulongfei.jump.rest.request.SendPrdRequest;
import com.sulongfei.jump.rest.response.RestResponse;
import com.sulongfei.jump.rest.response.BaseResponse;
import com.sulongfei.jump.service.TaskService;
import com.sulongfei.jump.utils.SerializeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/6/11 14:55
 * @Version 1.0
 */
@Slf4j
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class TaskServiceImpl implements TaskService {
    @Autowired
    private IntegralMapper integralMapper;
    @Autowired
    private SendGoodsMapper sendGoodsMapper;
    @Autowired
    private RedisService redisService;
    @Autowired
    private RestService restService;
    @Autowired
    private GlobalContext context;

    @Override
    @Transactional(readOnly = false)
    public void resetRank() {
        List<Long> clubIds = integralMapper.groupByClubId();
        clubIds.forEach(clubId -> {
            List<Integral> list = integralMapper.rankListTop(clubId, context.getEntryIntegral(), 3);
            redisService.set(Constants.RedisName.LAST_WEEK_RANK + clubId, new SerializeUtil<>(list));
        });
        integralMapper.resetRankList();
    }

    @Override
    @Transactional(readOnly = false)
    public void savePrd(SendGoods sendGoods) {
        sendGoodsMapper.insertSelective(sendGoods);
    }

    @Override
    public void sendPrd(SendPrdRequest request) {
        ResponseEntity<RestResponse<BaseResponse>> goodsResult = restService.sendPrd(request);
    }
}
