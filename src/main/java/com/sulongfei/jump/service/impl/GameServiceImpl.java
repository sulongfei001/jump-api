package com.sulongfei.jump.service.impl;

import com.google.common.collect.Lists;
import com.sulongfei.jump.constants.ResponseStatus;
import com.sulongfei.jump.context.GlobalContext;
import com.sulongfei.jump.dto.BaseDTO;
import com.sulongfei.jump.exception.JumpException;
import com.sulongfei.jump.mapper.*;
import com.sulongfei.jump.model.*;
import com.sulongfei.jump.response.*;
import com.sulongfei.jump.rest.request.MarketOrderRequest;
import com.sulongfei.jump.rest.request.PrdRequest;
import com.sulongfei.jump.rest.response.MarketOrderResponse;
import com.sulongfei.jump.rest.response.PrdResponse;
import com.sulongfei.jump.rest.response.RestResponse;
import com.sulongfei.jump.service.GameService;
import com.sulongfei.jump.web.interceptor.UserInterceptor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    private RestService restService;
    @Autowired
    private SecurityUserMapper userMapper;
    @Autowired
    private RecordSimpleMapper recordSimpleMapper;
    @Autowired
    private GlobalContext globalContext;
    @Autowired
    private IntegralMapper integralMapper;
    @Autowired
    private ClubMapper clubMapper;
    @Autowired
    private SendGoodsMapper sendGoodsMapper;
    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public Response randomGameResult(BaseDTO dto) {
        RecordSimple record = recordSimpleMapper.randomResult(dto.getRemoteClubId(), UserInterceptor.getLocalUser().getId());
        if (record == null) return new Response();
        SecurityUser user = userMapper.selectByPrimaryKey(record.getUserId());
        if (user == null) return new Response();
        UserRes userRes = new UserRes();
        BeanUtils.copyProperties(user, userRes);
        Integer ownRank = integralMapper.findRankByUserId(dto.getRemoteClubId(), UserInterceptor.getLocalUser().getId());
        Integer rivalRank = integralMapper.findRankByUserId(dto.getRemoteClubId(), user.getId());
        RandomResultRes data = new RandomResultRes(record.getIntegral(), userRes, ownRank, rivalRank);
        return new Response(data);
    }

    @Override
    public Response<PrdListRes> getPrdList(BaseDTO dto) {
        SecurityUser user = UserInterceptor.getLocalUser();
        PrdRequest request = new PrdRequest(user.getMemberId(), dto.getRemoteClubId());
        ResponseEntity<RestResponse<List<PrdResponse>>> result = restService.getPrdList(request);
        MarketOrderRequest orderRequest = new MarketOrderRequest(user.getMemberId(), dto.getRemoteClubId());
        ResponseEntity<RestResponse<List<MarketOrderResponse>>> orderRes = restService.getMarketOrderList(orderRequest);
        List<SendGoods> list = sendGoodsMapper.selectByStatus(user.getMemberId(), dto.getRemoteClubId(), 0);
        Club club = clubMapper.selectByOrgId(dto.getRemoteClubId());

        if (!HttpStatus.OK.equals(result.getStatusCode()) || !"200".equals(result.getBody().getErrorCode())) {
            throw new JumpException(ResponseStatus.OTHER_EXCEPTION);
        }
        if (!HttpStatus.OK.equals(orderRes.getStatusCode()) || !"200".equals(orderRes.getBody().getErrorCode())) {
            throw new JumpException(ResponseStatus.OTHER_EXCEPTION);
        }
        List<PrdRes> exclusiveList = Lists.newArrayList();
        result.getBody().getResult().forEach(prd -> {
            if (prd.getUseType() == 3) {
                PrdRes res = new PrdRes();
                BeanUtils.copyProperties(prd, res);
                res.setUsePlace(club.getSupplierAddress());
                exclusiveList.add(res);
            }
        });
        List<SendGoodsRes> goodsList = Lists.newArrayList();
        list.forEach(sendGoods -> {
            Goods goods = goodsMapper.selectByGoodsId(sendGoods.getGoodsId());
            SendGoodsRes res = new SendGoodsRes();
            BeanUtils.copyProperties(goods,res);
            res.setId(sendGoods.getId());
            goodsList.add(res);
        });

        List<MarketOrderRes> marketOrderList = Lists.newArrayList();
        orderRes.getBody().getResult().forEach(order -> {
            MarketOrderRes res = new MarketOrderRes();
            BeanUtils.copyProperties(order, res);
            marketOrderList.add(res);
        });
        PrdListRes data = new PrdListRes(exclusiveList, goodsList, marketOrderList);
        return new Response<>(data);
    }

    @Override
    @Deprecated
    @Transactional(readOnly = false)
    public Response getTicket(BaseDTO dto) {
        SecurityUser user = userMapper.selectByPrimaryKey(UserInterceptor.getLocalUser().getId());
        if (user.getEverydayTicket()) {
            throw new JumpException(ResponseStatus.GOT_TICKET);
        }
        user.setTicketNum(user.getTicketNum() + globalContext.getEverydayTicketNum());
        user.setEverydayTicket(true);
        userMapper.updateByPrimaryKey(user);
        return new Response();
    }
}
