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
import com.sulongfei.jump.rest.response.BaseResponse;
import com.sulongfei.jump.rest.response.RestResponse;
import com.sulongfei.jump.service.RoomSpreadService;
import com.sulongfei.jump.utils.ExcelUtil;
import com.sulongfei.jump.utils.IntegralConfig;
import com.sulongfei.jump.utils.SnowFlake;
import com.sulongfei.jump.utils.StrUtils;
import com.sulongfei.jump.web.interceptor.UserInterceptor;
import com.sulongfei.jump.web.socket.WebSocketServer;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/6/20 14:47
 * @Version 1.0
 */
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
        // =================系统校验及数据开始=================
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Long userId = UserInterceptor.getLocalUser().getId();
        RoomSpread roomSpread = roomSpreadMapper.selectByPrimaryKey(dto.getRoomId());
        if (roomSpread == null) throw new JumpException(ResponseStatus.NO_EXIST_ROOM);
        SecurityUser user = userMapper.selectByPrimaryKey(userId);
        Ticket ticket = ticketMapper.selectByClubId(userId, dto.getRemoteClubId());
        // =================系统校验及数据结束=================
        SettleRes res = new SettleRes();

        // 奖励门票
        ticket.setNum(ticket.getNum() + dto.getGetTicket());
        ticketMapper.updateByPrimaryKey(ticket);

        // =================分数计算开始=================
        IntegralConfig ic = ExcelUtil.integralConfig();
        List<Integer> randomCells = dto.getRandomCells();
        Integer gemstoneNum = randomCells.stream().filter(num -> dto.getPassCellNum() >= num).collect(Collectors.counting()).intValue();
        Integer countIntegral = dto.getPassCellNum() * ic.getCellIntegral() + ic.getVictoryIntegral() + ic.getVictoryGemstoneNum() * ic.getGemstoneIntegral() + gemstoneNum * ic.getGemstoneIntegral();
        Integer rankCountIntegral = countIntegral * roomSpread.getTicketNum();
        // =================分数计算结束=================

        // =================中奖逻辑开始=================
        Boolean win = false;
        if (roomSpread.getWinRecordId() == -1 && roomSpread.getPartakeNum() == roomSpread.getWinNum()) win = true;
        // =================中奖逻辑结束=================

        // =================记录分数及排行榜开始=================
        RecordSpread recordSpread = new RecordSpread(userId, dto.getRoomId(), dto.getPassCellNum(), win, roomSpread.getTicketNum(), dto.getGetTicket(), dto.getSaleId(), dto.getSaleType(), now);
        recordSpreadMapper.insertSelective(recordSpread);
        // 计算分数
        Integral integral = integralMapper.selectByUserIdClubId(userId, dto.getRemoteClubId());
        if (integral == null) {
            integral = new Integral(userId, dto.getRemoteClubId(), rankCountIntegral);
            integralMapper.insertSelective(integral);
            Integer currentRank = integralMapper.findRankByUserId(dto.getRemoteClubId(), userId);
            res.setCurrentRank(currentRank);
        } else {
            Integer formerRank = integralMapper.findRankByUserId(dto.getRemoteClubId(), userId);
            integral.setIntegral(integral.getIntegral() + rankCountIntegral);
            integralMapper.updateByPrimaryKey(integral);
            Integer laterRank = integralMapper.findRankByUserId(dto.getRemoteClubId(), userId);
            res.setRankUp(formerRank - laterRank);
        }
        // =================记录分数及排行榜结束=================

        if (win) roomSpread.setWinRecordId(recordSpread.getId());
        roomSpreadMapper.updateByPrimaryKey(roomSpread);

        // =================发送奖品开始=================
        if (roomSpread.getStatus() == 1) {
            SpreadGoods spreadGoods = spreadGoodsMapper.selectByPrimaryKey(roomSpread.getSpreadGoodsId());
            Goods goods = goodsMapper.selectByGoodsId(spreadGoods.getRemoteGoodsId());
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
                ResponseEntity<RestResponse<BaseResponse>> goodsResult = restService.sendPrd(goodsRequest);
            }
            Map<String, Object> map = new HashMap<>();
            map.put("type", 0);
            map.put("content", "恭喜" + user.getNickname() + "，刚刚获得了" + spreadGoods.getGoodsName());
            WebSocketServer.sendInfo(null, map);
            user.setConfirmPush(true);
            userMapper.updateByPrimaryKeySelective(user);
            Map<String, Object> newMap = new HashMap<>();
            newMap.put("type", 1);
            WebSocketServer.sendInfo(userId, newMap);
        }
        // =================发送奖品结束=================

        // =================返回结果=================
        res.setCountIntegral(integral.getIntegral());
        res.setSingleIntegral(rankCountIntegral);
        res.setStoneNum(ic.getVictoryGemstoneNum());
        res.setStoneIntegral(ic.getGemstoneIntegral());
        res.setWin(false);
        return new Response(res);
    }

    @Override
    @Transactional(readOnly = false)
    public Response spreadRoomGet(BaseDTO dto, String password) throws IOException {
        RoomSpread roomSpread = roomSpreadMapper.selectByPassword(password);
        SecurityUser user = userMapper.selectByPrimaryKey(UserInterceptor.getLocalUser().getId());
        Ticket ticket = ticketMapper.selectByClubId(user.getId(), dto.getRemoteClubId());
        if (roomSpread == null) throw new JumpException(ResponseStatus.NO_EXIST_ROOM);
        if (roomSpread.getStatus() == 1) throw new JumpException(ResponseStatus.ROOM_CLOSED);
        if (roomSpread.getTicketNum() > ticket.getNum()) throw new JumpException(ResponseStatus.NO_ENOUGH_TICKET);
        // 消耗门票
        ticket.setNum(ticket.getNum() - roomSpread.getTicketNum());
        ticketMapper.updateByPrimaryKey(ticket);
        // 房间人数+1
        roomSpread.setPartakeNum(roomSpread.getPartakeNum() + 1);
        if (roomSpread.getPartakeNum() >= roomSpread.getJoinNum()) roomSpread.setStatus((byte) 1);
        roomSpreadMapper.updateByPrimaryKey(roomSpread);

        RoomSpreadRes res = new RoomSpreadRes();
        BeanUtils.copyProperties(roomSpread, res);
        res.setRandomCells(ExcelUtil.getGameConfig());
        return new Response(res);
    }
}
