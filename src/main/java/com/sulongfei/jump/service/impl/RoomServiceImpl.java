package com.sulongfei.jump.service.impl;

import com.google.common.collect.Lists;
import com.sulongfei.jump.config.GlobalContext;
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
import com.sulongfei.jump.utils.SerializeUtil;
import com.sulongfei.jump.utils.StrUtils;
import com.sulongfei.jump.web.interceptor.UserInterceptor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Random;

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
    private RecordSimpleMapper recordSimpleMapper;
    @Autowired
    private RecordSpreadMapper recordSpreadMapper;
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
    @Autowired
    private GlobalContext globalContext;
    @Autowired
    private RedisService redisService;

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
        final int DEF_DIV_SCALE = 10;
        Long userId = UserInterceptor.getLocalUser().getId();
        SecurityUser user = userMapper.selectByPrimaryKey(userId);
        RoomSimple room = roomSimpleMapper.selectByPrimaryKey(dto.getRoomId());
        if (room == null) throw new JumpException(ResponseStatus.NO_EXIST_ROOM);
        Goods goods = goodsMapper.selectByGoodsId(room.getRemoteGoodsId());
        Integer ticketNum = room.getTicketNum();
        Integer consumeNum = room.getConsumeNum();
        Integer userTicketNum = user.getTicketNum();
        SettleRes res = new SettleRes();

        // 奖励门票
        user.setTicketNum(userTicketNum + dto.getGetTicket());
        userMapper.updateByPrimaryKeySelective(user);

        BigDecimal price = goods.getGoodsPrice();
        BigDecimal premium = BigDecimal.valueOf(room.getPremiumProportion()).divide(BigDecimal.valueOf(100), DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP);
        Double prize = room.getPrizeProbability() / (100 * 1.0);
        BigDecimal singlePrice = globalContext.getTicketSinglePrice();
        Integer mustNum = price.multiply(premium).divide(singlePrice, DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP).intValue();
        Integer prizeCount = recordSimpleMapper.countPrize(dto.getRoomId());
        Boolean randomOn = mustNum + prizeCount * (mustNum + 1) < consumeNum;
        Boolean win = false;
        if (randomOn && Math.random() <= prize) { // 中奖 -- 判断是寄送物品或者门店兑换
            win = true;
            // 发送大奖物品
            SendPrdRequest goodsRequest = new SendPrdRequest(user.getMemberId(), room.getRemoteClubId(), room.getRemoteGoodsId(), room.getGoodsNum(), dto.getSaleId(), dto.getSaleType());
            ResponseEntity<RestResponse<SendPrdResponse>> goodsResult = restService.sendPrd(goodsRequest);

        }

        // 记录结果
        RecordSimple recordSimple = new RecordSimple(userId, dto.getRoomId(), dto.getIntegral(), win, ticketNum, dto.getGetTicket(), dto.getSaleId(), dto.getSaleType(), now);
        recordSimpleMapper.insertSelective(recordSimple);
        // 计算分数
        Integral integral = integralMapper.selectByUserIdClubId(userId, dto.getRemoteClubId());
        if (integral == null) {
            integral = new Integral(userId, dto.getRemoteClubId(), dto.getIntegral());
            integralMapper.insertSelective(integral);
            Integer currentRank = integralMapper.findRankByUserId(dto.getRemoteClubId(), userId);
            res.setCurrentRank(currentRank);
        } else {
            Integer formerRank = integralMapper.findRankByUserId(dto.getRemoteClubId(), userId);
            integral.setIntegral(integral.getIntegral() + dto.getIntegral());
            integralMapper.updateByPrimaryKey(integral);
            Integer laterRank = integralMapper.findRankByUserId(dto.getRemoteClubId(), userId);
            res.setRankUp(formerRank - laterRank);
        }
        // 房间消耗门票总数
        room.setConsumeNum(consumeNum + ticketNum);
        roomSimpleMapper.updateByPrimaryKey(room);

        res.setCountIntegral(integral.getIntegral());
        res.setWin(win);
        return new Response(res);
    }

    @Override
    public Response rankList(BaseDTO dto) {
        SerializeUtil<List<Integral>> redisResult = redisService.get(Constants.RedisName.LAST_WEEK_RANK + dto.getRemoteClubId());
        List<Integral> integrals = integralMapper.rankListTop(dto.getRemoteClubId(), globalContext.getEntryIntegral(), globalContext.getEntryNum());
        List<RankPrize> rankPrizes = rankPrizeMapper.selectByClubId(dto.getRemoteClubId());

        RankListRes data = new RankListRes();
        if (redisResult != null && redisResult.getData() != null) {
            List<IntegralRes> lastWeekList = Lists.newArrayList();
            redisResult.getData().forEach(integral -> {
                IntegralRes integralRes = new IntegralRes();
                UserRes userRes = new UserRes();
                BeanUtils.copyProperties(integral.getUser(), userRes);
                integralRes.setIntegral(integral.getIntegral());
                integralRes.setUser(userRes);
                lastWeekList.add(integralRes);
            });
            data.setLastWeekList(lastWeekList);
        }
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

    @Override
    @Transactional(readOnly = false)
    public Response spreadRoomCreate(RoomSpreadDTO dto) {
        if (!UserInterceptor.getLocalUser().getIsSaler()) {
            throw new JumpException(ResponseStatus.NO_PERMISSION);
        }
        // 更改商品剩余库存
        SpreadGoods spreadGoods = spreadGoodsMapper.selectByPrimaryKey(dto.getSpreadGoodsId());
        Goods goods = goodsMapper.selectByGoodsId(spreadGoods.getRemoteGoodsId());
        if (goods.getRemainNum() < spreadGoods.getGoodsNum()) throw new JumpException(ResponseStatus.NO_ENOUGH_GOODS);

        goods.setRemainNum(goods.getRemainNum() - spreadGoods.getGoodsNum());
        goodsMapper.updateByPrimaryKey(goods);
        // 随机密码
        String password;
        Long id;
        do {
            password = StrUtils.randomNumber(6);
            id = roomSpreadMapper.checkPassword(password);
        } while (id != null);

        // 创建推广员房间
        RoomSpread roomSpread = new RoomSpread();
        roomSpread.setPassword(password);
        roomSpread.setRemoteClubId(dto.getRemoteClubId());
        roomSpread.setUserId(UserInterceptor.getLocalUser().getId());
        roomSpread.setCreateTime(new Timestamp(System.currentTimeMillis()));
        roomSpread.setSpreadGoodsId(dto.getSpreadGoodsId());
        roomSpread.setTicketNum(dto.getTicketNum());
        roomSpread.setJoinNum(dto.getJoinNum());
        roomSpread.setPartakeNum(0);
        roomSpread.setWinRecordId((long) -1);
        roomSpread.setStatus((byte) 0);
        roomSpread.setWinNum(new Random().nextInt(roomSpread.getJoinNum()) + 1);
        roomSpreadMapper.insertSelective(roomSpread);

        return new Response();
    }

    @Override
    public Response spreadRoomList(BaseDTO dto) {
        if (!UserInterceptor.getLocalUser().getIsSaler()) {
            throw new JumpException(ResponseStatus.NO_PERMISSION);
        }
        List<RoomSpread> list = roomSpreadMapper.selectAll(dto.getRemoteClubId(), UserInterceptor.getLocalUser().getId());
        List<RoomSpreadRes> data = Lists.newArrayList();
        list.forEach(roomSpread -> {
            RoomSpreadRes res = new RoomSpreadRes();
            BeanUtils.copyProperties(roomSpread, res);
            res.setCreateTime(roomSpread.getCreateTime().getTime());
            res.setEnded(roomSpread.getStatus() == 1);

            if (roomSpread.getSpreadGoods() != null) {
                SpreadGoodsRes goodsRes = new SpreadGoodsRes();
                BeanUtils.copyProperties(roomSpread.getSpreadGoods(), goodsRes);
                res.setSpreadGoods(goodsRes);
            }
            if (roomSpread.getWinRecordId() != null && roomSpread.getWinRecordId() != -1 && roomSpread.getStatus() == 1) {
                RecordSpread recordSpread = recordSpreadMapper.selectByPrimaryKey(roomSpread.getWinRecordId());
                if (recordSpread != null) {
                    SecurityUser securityUser = userMapper.selectByPrimaryKey(recordSpread.getId());
                    UserRes user = new UserRes();
                    BeanUtils.copyProperties(securityUser, user);
                    res.setUser(user);
                    res.setWinTime(recordSpread.getCreateTime().getTime());
                }
            }
            data.add(res);
        });
        return new Response(data);
    }

    @Override
    @Transactional(readOnly = false)
    public Response settleSpreadGame(SettleDTO dto) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Long userId = UserInterceptor.getLocalUser().getId();
        RoomSpread roomSpread = roomSpreadMapper.selectByPrimaryKey(dto.getRoomId());
        if (roomSpread == null) throw new JumpException(ResponseStatus.NO_EXIST_ROOM);
        SecurityUser user = userMapper.selectByPrimaryKey(userId);
        Integer userTicketNum = user.getTicketNum();
        SettleRes res = new SettleRes();

        if (roomSpread.getStatus() == 1) throw new JumpException(ResponseStatus.ROOM_CLOSED);

        // 奖励门票
        user.setTicketNum(userTicketNum + dto.getGetTicket());
        userMapper.updateByPrimaryKeySelective(user);

        Boolean win = false;
        if (roomSpread.getWinRecordId() == -1 && roomSpread.getPartakeNum() == roomSpread.getWinNum() - 1) win = true;


        // 记录结果
        RecordSpread recordSpread = new RecordSpread(userId, dto.getRoomId(), dto.getIntegral(), win, roomSpread.getTicketNum(), dto.getGetTicket(), dto.getSaleId(), dto.getSaleType(), now);
        recordSpreadMapper.insertSelective(recordSpread);
        // 计算分数
        Integral integral = integralMapper.selectByUserIdClubId(userId, dto.getRemoteClubId());
        if (integral == null) {
            integral = new Integral(userId, dto.getRemoteClubId(), dto.getIntegral());
            integralMapper.insertSelective(integral);
            Integer currentRank = integralMapper.findRankByUserId(dto.getRemoteClubId(), userId);
            res.setCurrentRank(currentRank);
        } else {
            Integer formerRank = integralMapper.findRankByUserId(dto.getRemoteClubId(), userId);
            integral.setIntegral(integral.getIntegral() + dto.getIntegral());
            integralMapper.updateByPrimaryKey(integral);
            Integer laterRank = integralMapper.findRankByUserId(dto.getRemoteClubId(), userId);
            res.setRankUp(formerRank - laterRank);
        }

        // 发送道具
        if (roomSpread.getPartakeNum() >= roomSpread.getJoinNum()){
            roomSpread.setStatus((byte) 1);
            // 发送大奖物品
        }

        // 参与次数+1
        roomSpread.setPartakeNum(roomSpread.getPartakeNum() == null ? 0 : roomSpread.getPartakeNum() + 1);
        roomSpread.setWinRecordId(win ? recordSpread.getId() : -1);
        roomSpreadMapper.updateByPrimaryKey(roomSpread);

        res.setCountIntegral(integral.getIntegral());
        res.setWin(false);
        return new Response(res);
    }

    @Override
    @Transactional(readOnly = false)
    public Response spreadRoomGet(BaseDTO dto, String password) {
        RoomSpread roomSpread = roomSpreadMapper.selectByPassword(password);
        SecurityUser user = userMapper.selectByPrimaryKey(UserInterceptor.getLocalUser().getId());
        if (roomSpread == null) throw new JumpException(ResponseStatus.NO_EXIST_ROOM);
        Integer ticketNum = roomSpread.getTicketNum();
        Integer userTicketNum = user.getTicketNum();
        if (ticketNum > userTicketNum) throw new JumpException(ResponseStatus.NO_ENOUGH_TICKET);
        // 消耗门票
        user.setTicketNum(userTicketNum - ticketNum);
        userMapper.updateByPrimaryKeySelective(user);

        RoomSpreadRes res = new RoomSpreadRes();
        BeanUtils.copyProperties(roomSpread, res);
        return new Response(res);
    }

    @Override
    @Transactional(readOnly = false)
    public Response roomSimpleGet(BaseDTO dto, Long roomId) {
        RoomSimple roomSimple = roomSimpleMapper.selectByPrimaryKey(roomId);
        SecurityUser user = userMapper.selectByPrimaryKey(UserInterceptor.getLocalUser().getId());
        if (roomSimple == null) throw new JumpException(ResponseStatus.NO_EXIST_ROOM);
        Integer ticketNum = roomSimple.getTicketNum();
        Integer userTicketNum = user.getTicketNum();
        if (ticketNum > userTicketNum) throw new JumpException(ResponseStatus.NO_ENOUGH_TICKET);
        // 消耗门票
        user.setTicketNum(userTicketNum - ticketNum);
        userMapper.updateByPrimaryKeySelective(user);

        RoomSimpleRes res = new RoomSimpleRes();
        BeanUtils.copyProperties(roomSimple, res);
        return new Response(res);
    }

}
