package com.sulongfei.jump.service.impl;

import com.google.common.collect.Lists;
import com.sulongfei.jump.config.GlobalValueConfig;
import com.sulongfei.jump.constants.Constants;
import com.sulongfei.jump.constants.ResponseStatus;
import com.sulongfei.jump.dto.BaseDTO;
import com.sulongfei.jump.dto.RoomSpreadDTO;
import com.sulongfei.jump.dto.SettleDTO;
import com.sulongfei.jump.exception.JumpException;
import com.sulongfei.jump.mapper.*;
import com.sulongfei.jump.model.*;
import com.sulongfei.jump.response.*;
import com.sulongfei.jump.rest.request.SendPrdRequest;
import com.sulongfei.jump.rest.response.RestResponse;
import com.sulongfei.jump.rest.response.SendPrdResponse;
import com.sulongfei.jump.service.RoomService;
import com.sulongfei.jump.web.interceptor.UserInterceptor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
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
    private RoomSpreadMapper roomSpreadMapper;
    @Autowired
    private RecordMapper recordMapper;
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private SpreadGoodsMapper spreadGoodsMapper;
    @Autowired
    private SecurityUserMapper userMapper;
    @Autowired
    private IntegralMapper integralMapper;
    @Autowired
    private RankPrizeMapper rankPrizeMapper;

    @Override
    public Response roomSimpleList(BaseDTO dto) {
        List<RoomSimple> list = roomSimpleMapper.selectRoomSimple(dto.getRemoteClubId());
        List<RoomSimpleRes> data = Lists.newArrayList();
        list.forEach(roomSimple -> {
            RoomSimpleRes roomSimpleRes = new RoomSimpleRes();
            BeanUtils.copyProperties(roomSimple, roomSimpleRes);
            data.add(roomSimpleRes);
        });
        return new Response(data);
    }

    @Override
    @Transactional(readOnly = false)
    public Response settleSimpleGame(SettleDTO dto) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        SecurityUser user = UserInterceptor.getLocalUser();
        final int DEF_DIV_SCALE = 10;

        RoomSimple room = roomSimpleMapper.selectByPrimaryKey(dto.getRoomId());
        Goods goods = goodsMapper.selectByGoodsId(room.getRemoteGoodsId());
        BigDecimal price = goods.getGoodsPrice();
        BigDecimal premium = BigDecimal.valueOf(room.getPremiumProportion()).divide(BigDecimal.valueOf(100), DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP);
        Double prize = room.getPrizeProbability() / (100 * 1.0);
        BigDecimal singlePrice = GlobalValueConfig.getTicketSinglePrice();
        Boolean randomOn = price.multiply(premium).divide(singlePrice, DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP).intValue() - room.getConsumeNum() > 0;
        Double n = Math.random();
        Boolean win = false;
        SettleRes data = null;

        if (randomOn && n <= prize) { // 中奖
            win = true;
            // 发送大奖物品
            if (!StringUtils.isEmpty(room.getRemoteClubId()) && !StringUtils.isEmpty(room.getGoodsNum()) && 0 < room.getGoodsNum()) {
                SendPrdRequest goodsRequest = new SendPrdRequest(user.getMemberId(), dto.getRemoteClubId(), room.getRemoteGoodsId(), room.getGoodsNum(), dto.getSaleId(), dto.getSaleType());
                ResponseEntity<RestResponse<SendPrdResponse>> goodsResult = restService.sendPrd(goodsRequest);
            }
        }

        // 发送卡券
        if (!StringUtils.isEmpty(dto.getCardId()) && !StringUtils.isEmpty(dto.getCardNum()) && dto.getCardNum() > 0) {
            SendPrdRequest cardRequest = new SendPrdRequest(user.getMemberId(), dto.getRemoteClubId(), dto.getCardId(), dto.getCardNum(), dto.getSaleId(), dto.getSaleType());
            ResponseEntity<RestResponse<SendPrdResponse>> cardResult = restService.sendPrd(cardRequest);
        }
        if (room.getTicketNum() > user.getTicketNum()) {
            throw new JumpException(ResponseStatus.NO_ENOUGH_TICKET);
        }
        // 消耗门票
        user.setTicketNum(user.getTicketNum() - room.getTicketNum());
        // 奖励门票
        user.setTicketNum(user.getTicketNum() + dto.getGetTicket());
        userMapper.updateByPrimaryKeySelective(user);
        // 记录结果
        Record record = new Record(
                user.getId(),
                dto.getRemoteClubId(),
                dto.getRoomId(),
                Constants.RoomType.SIMPLE,
                dto.getIntegral(),
                win,
                room.getTicketNum(),
                dto.getGetTicket(),
                dto.getSaleId(),
                dto.getSaleType(),
                now);
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
            data = new SettleRes(integral.getIntegral(), 3, win);
        }
        return new Response(data);
    }

    @Override
    public Response rankList(BaseDTO dto) {
        List<Integral> integrals = integralMapper.rankListTop(dto.getRemoteClubId(), GlobalValueConfig.getEntryIntegral(), GlobalValueConfig.getEntryNum());
        List<RankPrize> rankPrizes = rankPrizeMapper.selectByClubId(dto.getRemoteClubId());
        List<IntegralRes> list = Lists.newArrayList();
        List<PrizeRes> prizeList = Lists.newArrayList();
        integrals.forEach(integral -> {
            IntegralRes integralRes = new IntegralRes();
            UserRes userRes = new UserRes();
            BeanUtils.copyProperties(integral.getUser(), userRes);
            integralRes.setIntegral(integral.getIntegral());
            integralRes.setUser(userRes);
            list.add(integralRes);
        });
        rankPrizes.forEach(rankPrize -> {
            PrizeRes res = new PrizeRes();
            BeanUtils.copyProperties(rankPrize, res);
            prizeList.add(res);
        });
        RankListRes data = new RankListRes();
        UserRes userRes = new UserRes();
        BeanUtils.copyProperties(UserInterceptor.getLocalUser(), userRes);
        data.setList(list);
        data.setPrizeList(prizeList);
        data.setUser(userRes);
        data.setEntryIntegral(GlobalValueConfig.getEntryIntegral());
        return new Response(data);
    }

    @Override
    @Transactional(readOnly = false)
    public Response spreadRoomCreate(RoomSpreadDTO dto) {
        // 更改商品剩余库存
        SpreadGoods spreadGoods = spreadGoodsMapper.selectByPrimaryKey(dto.getSpreadGoodsId());
        Goods goods = goodsMapper.selectByGoodsId(spreadGoods.getRemoteGoodsId());
        if (goods.getRemainNum() < dto.getTicketNum()) {
            throw new JumpException(ResponseStatus.NO_ENOUGH_GOODS);
        }
        goods.setRemainNum(goods.getRemainNum() - dto.getTicketNum());
        goodsMapper.updateByPrimaryKey(goods);

        // 创建推广员房间
        RoomSpread roomSpread = new RoomSpread();
        roomSpread.setPassword("111111");
        roomSpread.setRemoteClubId(dto.getRemoteClubId());
        roomSpread.setSaleId(dto.getSaleId());
        roomSpread.setSaleType(dto.getSaleType());
        roomSpread.setCreateTime(new Timestamp(System.currentTimeMillis()));
        roomSpread.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
        roomSpread.setSpreadGoodsId(dto.getSpreadGoodsId());
        roomSpread.setTicketNum(dto.getTicketNum());
        roomSpread.setJoinNum(dto.getJoinNum());
        roomSpread.setPartakeNum(Integer.valueOf(Constants.Common.ZERO));
        roomSpread.setPrizeUserId(Long.valueOf(Constants.Common.MINUS_ONE));
        roomSpreadMapper.insertSelective(roomSpread);

        return new Response();
    }

    @Override
    public Response spreadRoomList(BaseDTO dto) {
        List<RoomSpread> list = roomSpreadMapper.selectEffective(dto.getRemoteClubId());
        List<RoomSpreadRes> data = Lists.newArrayList();
        list.forEach(roomSpread -> {
            SpreadGoodsRes goodsRes = null;
            if (roomSpread.getSpreadGoods() != null) {
                goodsRes = new SpreadGoodsRes();
                BeanUtils.copyProperties(roomSpread.getSpreadGoods(), goodsRes);
            }
            UserRes user = null;
            if (roomSpread.getUser() != null) {
                user = new UserRes();
                BeanUtils.copyProperties(roomSpread.getUser(), user);
            }
            RoomSpreadRes res = new RoomSpreadRes();
            BeanUtils.copyProperties(roomSpread, res);
            res.setCreateTime(roomSpread.getCreateTime().getTime());
            res.setLastUpdateTime(roomSpread.getLastUpdateTime().getTime());
            res.setSpreadGoods(goodsRes);
            res.setUser(user);
            res.setEnded(roomSpread.getPrizeUserId() == Long.valueOf(Constants.Common.MINUS_ONE) ? false : true);
            data.add(res);
        });
        return new Response(data);
    }
}
