package com.sulongfei.jump.service.impl;

import com.google.common.collect.Lists;
import com.sulongfei.jump.context.GlobalContext;
import com.sulongfei.jump.mapper.*;
import com.sulongfei.jump.model.Integral;
import com.sulongfei.jump.model.LastWeekIntegral;
import com.sulongfei.jump.model.RankPrize;
import com.sulongfei.jump.model.SendGoods;
import com.sulongfei.jump.rest.request.SendPrdRequest;
import com.sulongfei.jump.rest.response.BaseResponse;
import com.sulongfei.jump.rest.response.RestResponse;
import com.sulongfei.jump.service.TaskService;
import com.sulongfei.jump.utils.SnowFlake;
import com.sulongfei.jump.web.socket.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private SecurityUserMapper userMapper;
    @Autowired
    private IntegralMapper integralMapper;
    @Autowired
    private LastWeekIntegralMapper lastIntegralMapper;
    @Autowired
    private RankPrizeMapper rankPrizeMapper;
    @Autowired
    private SendGoodsMapper sendGoodsMapper;
    @Autowired
    private RestService restService;
    @Autowired
    private GlobalContext context;
    @Autowired
    @Qualifier("taskExecutor")
    private ThreadPoolTaskExecutor taskExecutor;

    @Override
    public void settle() {
        List<Long> clubIds = integralMapper.groupByClubId();
        clubIds.forEach(clubId -> {
            List<RankPrize> rankPrizes = rankPrizeMapper.selectByClubId(clubId);
            List<Integral> integrals = integralMapper.rankListTop(clubId, context.getEntryIntegral(), context.getEntryNum());
            for (int i = 0; i < integrals.size(); i++) {
                Integral integral = integrals.get(i);
                RankPrize prize = rankPrizes.get(i);
                if (prize.getGoods().getGoodsType() == 2) { // 寄送
                    SendGoods sendGoods = new SendGoods(
                            integral.getUser().getMemberId(),
                            clubId,
                            prize.getGoods().getRemoteGoodsId(),
                            prize.getGoodsNum(),
                            -1L,
                            -1,
                            new SnowFlake().nextId(),
                            new Timestamp(System.currentTimeMillis()),
                            (byte) 0);
                    taskExecutor.execute(() -> savePrd(sendGoods));
                } else if (prize.getGoods().getGoodsType() == 3) { // 门店兑换
                    SendPrdRequest goodsRequest = new SendPrdRequest(integral.getUser().getMemberId(), clubId, prize.getGoods().getRemoteGoodsId(), prize.getGoodsNum(), -1L, -1);
                    taskExecutor.execute(() -> sendPrd(goodsRequest));
                }
                integral.getUser().setConfirmPush(true);
                userMapper.updateByPrimaryKey(integral.getUser());
                Map<String, Object> newMap = new HashMap<>();
                newMap.put("type", 1);
                WebSocketServer.sendInfo(integral.getUserId(), newMap);
            }
        });
    }

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
                BeanUtils.copyProperties(integral, last);
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
