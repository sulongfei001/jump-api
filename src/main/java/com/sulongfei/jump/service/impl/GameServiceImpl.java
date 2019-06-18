package com.sulongfei.jump.service.impl;

import com.google.common.collect.Lists;
import com.sulongfei.jump.context.GlobalContext;
import com.sulongfei.jump.constants.ResponseStatus;
import com.sulongfei.jump.dto.BaseDTO;
import com.sulongfei.jump.exception.JumpException;
import com.sulongfei.jump.mapper.ClubMapper;
import com.sulongfei.jump.mapper.IntegralMapper;
import com.sulongfei.jump.mapper.RecordSimpleMapper;
import com.sulongfei.jump.mapper.SecurityUserMapper;
import com.sulongfei.jump.model.Club;
import com.sulongfei.jump.model.RecordSimple;
import com.sulongfei.jump.model.SecurityUser;
import com.sulongfei.jump.response.*;
import com.sulongfei.jump.rest.request.PrdRequest;
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

    @Override
    public Response randomGameResult(BaseDTO dto) {
        RecordSimple record = recordSimpleMapper.randomResult(dto.getRemoteClubId(), UserInterceptor.getLocalUser().getId());
        if (record == null) return new Response();
        SecurityUser user = userMapper.selectByPrimaryKey(record.getUserId());
        if (user == null) return new Response();
        UserRes userRes = new UserRes();
        BeanUtils.copyProperties(user, userRes);
        Integer ownRank = integralMapper.findRankByUserId(dto.getRemoteClubId(),UserInterceptor.getLocalUser().getId());
        Integer rivalRank = integralMapper.findRankByUserId(dto.getRemoteClubId(),user.getId());
        RandomResultRes data = new RandomResultRes(record.getIntegral(), userRes,ownRank,rivalRank);
        return new Response(data);
    }

    @Override
    public Response<PrdListRes> getPrdList(BaseDTO dto) {
        PrdRequest request = new PrdRequest(UserInterceptor.getLocalUser().getMemberId(), dto.getRemoteClubId(), 1L);
        ResponseEntity<RestResponse<List<PrdResponse>>> result = restService.getPrdList(request);
        if (!HttpStatus.OK.equals(result.getStatusCode()) || !"200".equals(result.getBody().getErrorCode())) {
            throw new JumpException(ResponseStatus.OTHER_EXCEPTION);
        }
        Club club = clubMapper.selectByOrgId(dto.getRemoteClubId());
        List<PrdRes> exchangeList = Lists.newArrayList();
        List<PrdRes> exclusiveList = Lists.newArrayList();
        result.getBody().getResult().forEach(prd -> {
            if (prd.getUseType() == 2) {
                PrdRes res = new PrdRes();
                BeanUtils.copyProperties(prd, res);
                res.setUsePlace(club.getSupplierAddress());
                exchangeList.add(res);
            } else if (prd.getUseType() == 3) {
                PrdRes res = new PrdRes();
                BeanUtils.copyProperties(prd, res);
                res.setUsePlace(club.getSupplierAddress());
                exclusiveList.add(res);
            }
        });
        PrdListRes data = new PrdListRes(exchangeList, exclusiveList);
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
