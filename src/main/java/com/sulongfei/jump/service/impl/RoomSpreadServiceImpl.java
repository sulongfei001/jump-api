package com.sulongfei.jump.service.impl;

import com.google.common.collect.Lists;
import com.sulongfei.jump.constants.ResponseStatus;
import com.sulongfei.jump.dto.BaseDTO;
import com.sulongfei.jump.dto.RoomSpreadDTO;
import com.sulongfei.jump.dto.SettleDTO;
import com.sulongfei.jump.exception.JumpException;
import com.sulongfei.jump.mapper.*;
import com.sulongfei.jump.model.*;
import com.sulongfei.jump.response.*;
import com.sulongfei.jump.rest.request.SendPrdRequest;
import com.sulongfei.jump.service.RoomSpreadService;
import com.sulongfei.jump.utils.ExcelUtil;
import com.sulongfei.jump.utils.IntegralConfig;
import com.sulongfei.jump.utils.SnowFlake;
import com.sulongfei.jump.utils.StrUtils;
import com.sulongfei.jump.web.interceptor.UserInterceptor;
import com.sulongfei.jump.web.socket.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/6/20 14:47
 * @Version 1.0
 */
@Slf4j
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class RoomSpreadServiceImpl implements RoomSpreadService {
    @Autowired
    private RoomSpreadMapper roomSpreadMapper;
    @Autowired
    private RecordSpreadMapper recordSpreadMapper;
    @Autowired
    private SpreadGoodsMapper spreadGoodsMapper;
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private SecurityUserMapper userMapper;
    @Autowired
    private TicketMapper ticketMapper;
    @Autowired
    private IntegralMapper integralMapper;
    @Autowired
    private SendGoodsMapper sendGoodsMapper;
    @Autowired
    private RestService restService;
    @Autowired
    @Qualifier("taskExecutor")
    private ThreadPoolTaskExecutor taskExecutor;
    @Autowired
    @Qualifier("taskScheduler")
    private ThreadPoolTaskScheduler taskScheduler;


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
        roomSpread.setCreateUserId(UserInterceptor.getLocalUser().getId());
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
                // 获胜记录
                RecordSpread recordSpread = recordSpreadMapper.selectByPrimaryKey(roomSpread.getWinRecordId());
                if (recordSpread != null) {
                    SecurityUser securityUser = userMapper.selectByPrimaryKey(recordSpread.getUserId());
                    if (securityUser != null) {
                        UserRes user = new UserRes();
                        BeanUtils.copyProperties(securityUser, user);
                        res.setUser(user);
                        res.setWinTime(recordSpread.getCreateTime().getTime());
                    }
                }
                // 参与次数最多的人
                Long userId = recordSpreadMapper.mostTimesUser(roomSpread.getId());
                SecurityUser securityUser = userMapper.selectByPrimaryKey(userId);
                if (securityUser != null) {
                    UserRes user = new UserRes();
                    BeanUtils.copyProperties(securityUser, user);
                    res.setMostUser(user);
                }
            }
            data.add(res);
        });
        return new Response(data);
    }

    @Override
    @Transactional(readOnly = false)
    public Response settleSpreadGame(SettleDTO dto) throws IOException {
        SettleRes res = new SettleRes();
        // =================系统校验及数据开始=================
        Long userId = UserInterceptor.getLocalUser().getId();
        RoomSpread roomSpread = roomSpreadMapper.selectByPrimaryKey(dto.getRoomId());
        if (roomSpread == null) throw new JumpException(ResponseStatus.NO_EXIST_ROOM);

        // =================分数计算开始=================
        IntegralConfig ic = ExcelUtil.integralConfig();
        List<Integer> randomCells = dto.getRandomCells();
        Integer gemstoneNum = randomCells.stream().filter(num -> dto.getPassCellNum() >= num).collect(Collectors.counting()).intValue();
        Integer countIntegral = dto.getPassCellNum() * ic.getCellIntegral() + ic.getVictoryIntegral() + ic.getVictoryGemstoneNum() * ic.getGemstoneIntegral() + gemstoneNum * ic.getGemstoneIntegral();
        Integer rankCountIntegral = countIntegral * roomSpread.getTicketNum();
        Integer baseIntegral = (ic.getVictoryIntegral() + ic.getVictoryGemstoneNum() * ic.getGemstoneIntegral()) * roomSpread.getTicketNum();

        // =================分数修改开始=================
        RecordSpread recordSpread = recordSpreadMapper.selectByPrimaryKey(dto.getRecordId());
        if (recordSpread.getSettled()) throw new JumpException(ResponseStatus.GAME_SETTLED);
        recordSpread.setIntegral(dto.getPassCellNum());
        recordSpread.setSettled(true);
        recordSpreadMapper.updateByPrimaryKey(recordSpread);
        Integral integral = integralMapper.selectByUserIdClubId(userId, dto.getRemoteClubId());
        integral.setIntegral(integral.getIntegral() + rankCountIntegral - baseIntegral);
        integralMapper.updateByPrimaryKey(integral);
        Integer latterRank = integralMapper.findRankByUserId(dto.getRemoteClubId(), userId);

        // =================返回结果=================
        res.setCountIntegral(integral.getIntegral());
        res.setSingleIntegral(rankCountIntegral);
        res.setStoneNum(ic.getVictoryGemstoneNum());
        res.setStoneIntegral(ic.getGemstoneIntegral());
        res.setLatterRank(latterRank);
        res.setWin(false);
        return new Response(res);
    }

    @Override
    @Transactional(readOnly = false)
    public Response spreadRoomGet(BaseDTO dto, String password) throws IOException {
        // =================系统校验及数据开始=================
        RoomSpread roomSpread = roomSpreadMapper.selectByPassword(password);
        Long userId = UserInterceptor.getLocalUser().getId();
        SecurityUser user = userMapper.selectByPrimaryKey(userId);
        Ticket ticket = ticketMapper.selectByClubId(user.getId(), dto.getRemoteClubId());
        if (roomSpread == null) throw new JumpException(ResponseStatus.NO_EXIST_ROOM);
        if (roomSpread.getStatus() == 1) throw new JumpException(ResponseStatus.ROOM_CLOSED);
        if (roomSpread.getTicketNum() > ticket.getNum()) throw new JumpException(ResponseStatus.NO_ENOUGH_TICKET);
        SpreadGoods spreadGoods = spreadGoodsMapper.selectByPrimaryKey(roomSpread.getSpreadGoodsId());
        Goods goods = goodsMapper.selectByGoodsId(spreadGoods.getRemoteGoodsId());
        // 消耗门票
        ticket.setNum(ticket.getNum() - roomSpread.getTicketNum());
        ticketMapper.updateByPrimaryKey(ticket);
        // 房间人数+1
        roomSpread.setPartakeNum(roomSpread.getPartakeNum() + 1);
        if (roomSpread.getPartakeNum() >= roomSpread.getJoinNum()) roomSpread.setStatus((byte) 1);

        // =================分数计算开始=================
        IntegralConfig ic = ExcelUtil.integralConfig();
        Integer baseIntegral = (ic.getVictoryIntegral() + ic.getVictoryGemstoneNum() * ic.getGemstoneIntegral()) * roomSpread.getTicketNum();

        // =================记录分数及排行榜开始=================
        RecordSpread recordSpread = new RecordSpread(userId, roomSpread.getId(), 0, roomSpread.getPartakeNum() == roomSpread.getWinNum(), roomSpread.getTicketNum(), 0, dto.getSaleId(), dto.getSaleType(), new Timestamp(System.currentTimeMillis()), false);
        recordSpreadMapper.insertSelective(recordSpread);
        Integer formerRank = integralMapper.findRankByUserId(dto.getRemoteClubId(), userId);
        Integral integral = integralMapper.selectByUserIdClubId(userId, dto.getRemoteClubId());
        if (integral == null) {
            integral = new Integral(userId, dto.getRemoteClubId(), baseIntegral);
            integralMapper.insertSelective(integral);
        } else {
            integral.setIntegral(integral.getIntegral() + baseIntegral);
            integralMapper.updateByPrimaryKey(integral);
        }

        if (roomSpread.getPartakeNum() == roomSpread.getWinNum()) roomSpread.setWinRecordId(recordSpread.getId());
        roomSpreadMapper.updateByPrimaryKey(roomSpread);

        if (roomSpread.getStatus() == 1) {
            taskExecutor.execute(() -> {
                log.info("==================发送卡券==================");
                if (goods.getGoodsType() == 2) { // 寄送
                    SendGoods sendGoods = new SendGoods(
                            user.getMemberId(),
                            dto.getRemoteClubId(),
                            goods.getRemoteGoodsId(),
                            spreadGoods.getGoodsNum(),
                            dto.getSaleId(),
                            dto.getSaleType(),
                            new SnowFlake().nextId(),
                            new Timestamp(System.currentTimeMillis()),
                            (byte) 0);
                    sendGoodsMapper.insertSelective(sendGoods);
                } else if (goods.getGoodsType() == 3) { // 门店兑换
                    SendPrdRequest goodsRequest = new SendPrdRequest(user.getMemberId(), roomSpread.getRemoteClubId(), spreadGoods.getRemoteGoodsId(), spreadGoods.getGoodsNum(), dto.getSaleId(), dto.getSaleType());
                    restService.sendPrd(goodsRequest);
                }
            });
            taskScheduler.schedule(() -> {
                log.info("==================发送公告==================");
                Map<String, Object> map = new HashMap<>();
                map.put("type", 0);
                map.put("content", "恭喜" + user.getNickname() + "，刚刚获得了" + spreadGoods.getGoodsName());
                WebSocketServer.sendInfo(null, map);
                user.setConfirmPush(true);
                userMapper.updateByPrimaryKeySelective(user);
                Map<String, Object> newMap = new HashMap<>();
                newMap.put("type", 1);
                WebSocketServer.sendInfo(user.getId(), newMap);
            }, new Date(new Date().getTime() + 30 * 1000));
        }

        RoomRes res = new RoomRes();
        res.setId(roomSpread.getId());
        res.setJoinNum(roomSpread.getJoinNum());
        res.setPartakeNum(roomSpread.getPartakeNum());
        res.setTicketNum(roomSpread.getTicketNum());
        res.setGoodsName(spreadGoods.getGoodsName());
        res.setRandomCells(ExcelUtil.getGameConfig());
        res.setRecordId(recordSpread.getId());
        res.setFormerRank(formerRank);
        return new Response(res);
    }
}
