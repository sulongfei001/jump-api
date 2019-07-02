package com.sulongfei.jump.service.impl;

import com.google.common.collect.Lists;
import com.sulongfei.jump.context.GlobalContext;
import com.sulongfei.jump.mapper.IntegralMapper;
import com.sulongfei.jump.mapper.LastWeekIntegralMapper;
import com.sulongfei.jump.mapper.SendGoodsMapper;
import com.sulongfei.jump.model.Integral;
import com.sulongfei.jump.model.LastWeekIntegral;
import com.sulongfei.jump.model.SendGoods;
import com.sulongfei.jump.rest.request.SendPrdRequest;
import com.sulongfei.jump.rest.response.RestResponse;
import com.sulongfei.jump.rest.response.BaseResponse;
import com.sulongfei.jump.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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
    private LastWeekIntegralMapper lastIntegralMapper;
    @Autowired
    private SendGoodsMapper sendGoodsMapper;
    @Autowired
    private RestService restService;
    @Autowired
    private GlobalContext context;

    @Override
    @Transactional(readOnly = false)
    public void resetRank() {
        List<Long> clubIds = integralMapper.groupByClubId();
        lastIntegralMapper.deleteAll();
        clubIds.forEach(clubId -> {
            List<Integral> list = integralMapper.rankListTop(clubId, context.getEntryIntegral(), 3);
            List<LastWeekIntegral> integrals = Lists.newArrayList();
            list.forEach(integral -> {
                LastWeekIntegral last = new LastWeekIntegral();
                BeanUtils.copyProperties(integral,last);
                integrals.add(last);
            });
            lastIntegralMapper.batchInsert(integrals);
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
