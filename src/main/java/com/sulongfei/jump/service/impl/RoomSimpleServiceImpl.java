package com.sulongfei.jump.service.impl;

import com.google.common.collect.Lists;
import com.sulongfei.jump.constants.ResponseStatus;
import com.sulongfei.jump.context.GlobalContext;
import com.sulongfei.jump.dto.BaseDTO;
import com.sulongfei.jump.dto.SettleDTO;
import com.sulongfei.jump.exception.JumpException;
import com.sulongfei.jump.mapper.*;
import com.sulongfei.jump.model.*;
import com.sulongfei.jump.response.*;
import com.sulongfei.jump.rest.request.SendPrdRequest;
import com.sulongfei.jump.service.RoomSimpleService;
import com.sulongfei.jump.utils.ExcelUtil;
import com.sulongfei.jump.utils.IntegralConfig;
import com.sulongfei.jump.utils.SnowFlake;
import com.sulongfei.jump.web.interceptor.UserInterceptor;
import com.sulongfei.jump.web.socket.WebSocketServer;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/5/30 14:33
 * @Version 1.0
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class RoomSimpleServiceImpl implements RoomSimpleService {
    @Autowired
    private RoomSimpleMapper roomSimpleMapper;
    @Autowired
    private RecordSimpleMapper recordSimpleMapper;
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private SecurityUserMapper userMapper;
    @Autowired
    private TicketMapper ticketMapper;
    @Autowired
    private IntegralMapper integralMapper;
    @Autowired
    private LastWeekIntegralMapper lastIntegralMapper;
    @Autowired
    private RankPrizeMapper rankPrizeMapper;
    @Autowired
    private GlobalContext globalContext;
    @Autowired
    private SendGoodsMapper sendGoodsMapper;
    @Autowired
    private RestService restService;
    @Autowired
    @Qualifier("taskExecutor")
    private ThreadPoolTaskExecutor taskExecutor;

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
    public Response settleSimpleGame(SettleDTO dto) throws IOException {
        final int DEF_DIV_SCALE = 10;
        Long userId = UserInterceptor.getLocalUser().getId();
        RoomSimple roomSimple = roomSimpleMapper.selectByPrimaryKey(dto.getRoomId());
        if (roomSimple == null) throw new JumpException(ResponseStatus.NO_EXIST_ROOM);
        SecurityUser user = userMapper.selectByPrimaryKey(userId);
        Goods goods = goodsMapper.selectByGoodsId(roomSimple.getRemoteGoodsId());
        SettleRes res = new SettleRes();

        // =================分数计算开始=================
        Integer countIntegral;
        Integer rankCountIntegral;
        IntegralConfig ic = ExcelUtil.integralConfig();
        List<Integer> randomCells = dto.getRandomCells();
        Integer gemstoneNum = randomCells.stream().filter(num -> dto.getPassCellNum() >= num).collect(Collectors.counting()).intValue();
        if (dto.getIsWin()) {
            countIntegral = dto.getPassCellNum() * ic.getCellIntegral() + ic.getVictoryIntegral() + ic.getVictoryGemstoneNum() * ic.getGemstoneIntegral() + gemstoneNum * ic.getGemstoneIntegral();
            res.setStoneNum(ic.getVictoryGemstoneNum());
        } else {
            countIntegral = dto.getPassCellNum() * ic.getCellIntegral() + ic.getDefeatIntegral() + ic.getDefeatGemstoneNum() * ic.getGemstoneIntegral() + gemstoneNum * ic.getGemstoneIntegral();
            res.setStoneNum(ic.getDefeatGemstoneNum());
        }
        rankCountIntegral = countIntegral * roomSimple.getTicketNum();
        Integer baseIntegral = (ic.getDefeatIntegral() + ic.getDefeatGemstoneNum() * ic.getGemstoneIntegral()) * roomSimple.getTicketNum();
        // =================分数计算结束=================

        // =================中奖概率计算=================
        Boolean win = false;
        BigDecimal price = goods.getGoodsPrice();
        BigDecimal premium = BigDecimal.valueOf(roomSimple.getPremiumProportion()).divide(BigDecimal.valueOf(100), DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP);
        Double prize = roomSimple.getPrizeProbability() / (100 * 1.0);
        BigDecimal singlePrice = globalContext.getTicketSinglePrice();
        Integer mustNum = price.multiply(premium).divide(singlePrice, DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP).intValue();
        Integer prizeCount = recordSimpleMapper.countPrize(dto.getRoomId());
        Boolean randomOn = mustNum + prizeCount * (mustNum + 1) < roomSimple.getConsumeNum();
        if (randomOn && Math.random() <= prize) win = true;
        // =================中奖概率计算=================

        RecordSimple recordSimple = recordSimpleMapper.selectByPrimaryKey(dto.getRecordId());
        recordSimple.setIntegral(dto.getPassCellNum());
        recordSimple.setIsWin(win);
        recordSimpleMapper.updateByPrimaryKey(recordSimple);
        Integral integral = integralMapper.selectByUserIdClubId(userId, dto.getRemoteClubId());
        integral.setIntegral(integral.getIntegral() + rankCountIntegral - baseIntegral);
        integralMapper.updateByPrimaryKey(integral);
        Integer latterRank = integralMapper.findRankByUserId(dto.getRemoteClubId(), userId);

        if (win) {
            taskExecutor.execute(() -> {
                if (goods.getGoodsType() == 2) { // 寄送
                    SendGoods sendGoods = new SendGoods(
                            user.getMemberId(),
                            dto.getRemoteClubId(),
                            goods.getRemoteGoodsId(),
                            roomSimple.getGoodsNum(),
                            dto.getSaleId(),
                            dto.getSaleType(),
                            new SnowFlake().nextId(),
                            new Timestamp(System.currentTimeMillis()),
                            (byte) 0);
                    sendGoodsMapper.insertSelective(sendGoods);
                } else if (goods.getGoodsType() == 3) { // 门店兑换
                    SendPrdRequest goodsRequest = new SendPrdRequest(user.getMemberId(), roomSimple.getRemoteClubId(), roomSimple.getRemoteGoodsId(), roomSimple.getGoodsNum(), dto.getSaleId(), dto.getSaleType());
                    restService.sendPrd(goodsRequest);
                }
                Map<String, Object> map = new HashMap<>();
                map.put("type", 0);
                map.put("content", "恭喜" + user.getNickname() + "，刚刚获得了" + goods.getGoodsName());
                WebSocketServer.sendInfo(null, map);
                user.setConfirmPush(true);
                userMapper.updateByPrimaryKey(user);
                Map<String, Object> newMap = new HashMap<>();
                newMap.put("type", 1);
                WebSocketServer.sendInfo(userId, newMap);
            });
        }

        res.setCountIntegral(integral.getIntegral());
        res.setSingleIntegral(rankCountIntegral);
        res.setWin(win);
        res.setStoneIntegral(ic.getGemstoneIntegral());
        res.setGoodsPicture(goods.getGoodsPicture());
        res.setLatterRank(latterRank);
        return new Response(res);
    }

    @Override
    @Transactional(readOnly = false)
    public Response roomSimpleGet(BaseDTO dto, Long roomId) throws IOException {
        RoomSimple roomSimple = roomSimpleMapper.selectByPrimaryKey(roomId);
        Long userId = UserInterceptor.getLocalUser().getId();
        SecurityUser user = userMapper.selectByPrimaryKey(userId);
        Ticket ticket = ticketMapper.selectByClubId(user.getId(), dto.getRemoteClubId());
        if (roomSimple == null) throw new JumpException(ResponseStatus.NO_EXIST_ROOM);
        if (roomSimple.getTicketNum() > ticket.getNum()) throw new JumpException(ResponseStatus.NO_ENOUGH_TICKET);
        // 消耗门票
        ticket.setNum(ticket.getNum() - roomSimple.getTicketNum());
        ticketMapper.updateByPrimaryKey(ticket);

        // =================分数计算开始=================
        IntegralConfig ic = ExcelUtil.integralConfig();
        Integer baseIntegral = (ic.getDefeatIntegral() + ic.getDefeatGemstoneNum() * ic.getGemstoneIntegral()) * roomSimple.getTicketNum();
        // =================分数计算结束=================

        // =================记录分数及排行榜开始=================
        RecordSimple recordSimple = new RecordSimple(userId, roomId, 0, false, roomSimple.getTicketNum(), 0, dto.getSaleId(), dto.getSaleType(), new Timestamp(System.currentTimeMillis()));
        recordSimpleMapper.insertSelective(recordSimple);
        // 计算分数
        Integer formerRank = integralMapper.findRankByUserId(dto.getRemoteClubId(), userId);
        Integral integral = integralMapper.selectByUserIdClubId(userId, dto.getRemoteClubId());
        if (integral == null) {
            integral = new Integral(userId, dto.getRemoteClubId(), baseIntegral);
            integralMapper.insertSelective(integral);
        } else {
            integral.setIntegral(integral.getIntegral() + baseIntegral);
            integralMapper.updateByPrimaryKey(integral);
        }
        // =================记录分数及排行榜结束=================

        roomSimple.setConsumeNum(roomSimple.getConsumeNum() + roomSimple.getTicketNum());
        roomSimpleMapper.updateByPrimaryKey(roomSimple);

        RoomRes res = new RoomRes();
        res.setId(roomSimple.getId());
        res.setTicketNum(roomSimple.getTicketNum());
        res.setGoodsName(roomSimple.getGoodsName());
        res.setRandomCells(ExcelUtil.getGameConfig());
        res.setRecordId(recordSimple.getId());
        res.setFormerRank(formerRank);
        return new Response(res);
    }

    @Override
    public Response rankList(BaseDTO dto) {
        List<LastWeekIntegral> lastIntegrals = lastIntegralMapper.selectByClubId(dto.getRemoteClubId());
        List<Integral> integrals = integralMapper.rankListTop(dto.getRemoteClubId(), globalContext.getEntryIntegral(), globalContext.getEntryNum());
        List<RankPrize> rankPrizes = rankPrizeMapper.selectByClubId(dto.getRemoteClubId());

        RankListRes data = new RankListRes();
        List<IntegralRes> lastWeekList = Lists.newArrayList();
        lastIntegrals.forEach(integral -> {
            IntegralRes integralRes = new IntegralRes();
            UserRes userRes = new UserRes();
            BeanUtils.copyProperties(integral.getUser(), userRes);
            integralRes.setIntegral(integral.getIntegral());
            integralRes.setUser(userRes);
            lastWeekList.add(integralRes);
        });
        data.setLastWeekList(lastWeekList);

        List<IntegralRes> list = Lists.newArrayList();
        integrals.forEach(integral -> {
            IntegralRes integralRes = new IntegralRes();
            UserRes userRes = new UserRes();
            BeanUtils.copyProperties(integral.getUser(), userRes);
            integralRes.setIntegral(integral.getIntegral());
            integralRes.setUser(userRes);
            list.add(integralRes);
        });
        List<PrizeRes> prizeList = Lists.newArrayList();
        rankPrizes.forEach(rankPrize -> {
            PrizeRes res = new PrizeRes();
            BeanUtils.copyProperties(rankPrize, res);
            res.setGoodsPicture(rankPrize.getGoods().getGoodsPicture());
            prizeList.add(res);
        });
        UserRes userRes = new UserRes();
        BeanUtils.copyProperties(UserInterceptor.getLocalUser(), userRes);
        data.setList(list);
        data.setPrizeList(prizeList);
        data.setUser(userRes);
        data.setEntryIntegral(globalContext.getEntryIntegral());
        return new Response(data);
    }

}
