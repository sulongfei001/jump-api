package com.sulongfei.jump.service.impl;

import com.google.common.collect.Lists;
import com.sulongfei.jump.config.GlobalValueConfig;
import com.sulongfei.jump.constants.ResponseStatus;
import com.sulongfei.jump.dto.BaseDTO;
import com.sulongfei.jump.dto.SettleDTO;
import com.sulongfei.jump.exception.JumpException;
import com.sulongfei.jump.mapper.IntegralMapper;
import com.sulongfei.jump.mapper.RecordMapper;
import com.sulongfei.jump.mapper.RoomSimpleMapper;
import com.sulongfei.jump.mapper.SecurityUserMapper;
import com.sulongfei.jump.model.Integral;
import com.sulongfei.jump.model.Record;
import com.sulongfei.jump.model.RoomSimple;
import com.sulongfei.jump.model.SecurityUser;
import com.sulongfei.jump.response.*;
import com.sulongfei.jump.rest.request.SendPrdRequest;
import com.sulongfei.jump.rest.response.SendPrdResponse;
import com.sulongfei.jump.service.RestService;
import com.sulongfei.jump.service.RoomService;
import com.sulongfei.jump.web.interceptor.UserInterceptor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.List;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/5/30 14:33
 * @Version 1.0
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class RoomServiceImpl implements RoomService {
    @Autowired
    private RestService restService;
    @Autowired
    private RoomSimpleMapper roomSimpleMapper;
    @Autowired
    private RecordMapper recordMapper;
    @Autowired
    private SecurityUserMapper userMapper;
    @Autowired
    private IntegralMapper integralMapper;

    @Override
    public Response roomSimpleList(BaseDTO dto) {
        List<RoomSimple> list = roomSimpleMapper.selectRoomSimple(dto.getRemoteClubId());
        List<RoomSimpleRes> data = Lists.newArrayList();
        list.forEach(roomSimple -> {
            RoomSimpleRes roomSimpleRes = new RoomSimpleRes();
            GoodsRes goodsRes = new GoodsRes();
            BeanUtils.copyProperties(roomSimple, roomSimpleRes);
            BeanUtils.copyProperties(roomSimple, goodsRes);
            roomSimpleRes.setGoods(goodsRes);
            data.add(roomSimpleRes);
        });
        return new Response(data);
    }

    @Override
    @Transactional(readOnly = false)
    public Response settleSimpleGame(SettleDTO dto) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        SecurityUser user = UserInterceptor.getLocalUser();
        // 发送大奖物品
        if (!StringUtils.isEmpty(dto.getGoodsId()) && !StringUtils.isEmpty(dto.getGoodsNum()) && 0 < dto.getGoodsNum()) {
            SendPrdRequest goodsRequest = new SendPrdRequest(user.getMemberId(), dto.getRemoteClubId(), dto.getGoodsId(), dto.getGoodsNum(), dto.getSaleId(), dto.getSaleType());
            ResponseEntity<SendPrdResponse> goodsResult = restService.sendPrd(goodsRequest);
        }
        // 发送卡券
        if (!StringUtils.isEmpty(dto.getCardId()) && !StringUtils.isEmpty(dto.getCardNum()) && dto.getCardNum() > 0) {
            SendPrdRequest cardRequest = new SendPrdRequest(user.getMemberId(), dto.getRemoteClubId(), dto.getCardId(), dto.getCardNum(), dto.getSaleId(), dto.getSaleType());
            ResponseEntity<SendPrdResponse> cardResult = restService.sendPrd(cardRequest);
        }
        if (dto.getConsumeTicket() > user.getTicketNum()) {
            throw new JumpException(ResponseStatus.NO_ENOUGH_TICKET);
        }
        // 消耗门票
        user.setTicketNum(user.getTicketNum() - dto.getConsumeTicket());
        // 奖励门票
        user.setTicketNum(user.getTicketNum() + dto.getGetTicket());
        userMapper.updateByPrimaryKeySelective(user);
        // 记录结果
        Record record = new Record(user.getId(), dto.getRemoteClubId(), dto.getConsumeTicket(), dto.getSaleId(), dto.getSaleType(), now);
        recordMapper.insertSelective(record);
        // 计算分数
        if (dto.getIntegral() > 0) {
            Integral integral = integralMapper.selectByUserIdClubId(user.getId(), dto.getRemoteClubId());
            if (integral == null) {
                integral = new Integral(user.getId(), dto.getRemoteClubId(), dto.getIntegral());
                integralMapper.insertSelective(integral);
            } else {
                integral.setIntegral(integral.getIntegral() + dto.getIntegral());
                integralMapper.updateByPrimaryKey(integral);
            }
        }
        return new Response();
    }

    @Override
    public Response rankList(BaseDTO dto) {
        List<Integral> integrals = integralMapper.rankListTop(dto.getRemoteClubId(), GlobalValueConfig.getEntryIntegral(), GlobalValueConfig.getEntryNum());
        List<IntegralRes> list = Lists.newArrayList();
        integrals.forEach(integral -> {
            IntegralRes integralRes = new IntegralRes();
            UserRes userRes = new UserRes();
            BeanUtils.copyProperties(integral.getUser(), userRes);
            integralRes.setIntegral(integral.getIntegral());
            integralRes.setUser(userRes);
            list.add(integralRes);
        });
        RankListRes data = new RankListRes();
        UserRes userRes = new UserRes();
        BeanUtils.copyProperties(UserInterceptor.getLocalUser(), userRes);
        data.setList(list);
        data.setUser(userRes);
        data.setEntryIntegral(GlobalValueConfig.getEntryIntegral());
        return new Response(data);
    }
}
