package com.sulongfei.jump.service.impl;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.sulongfei.jump.constants.ResponseStatus;
import com.sulongfei.jump.dto.BaseDTO;
import com.sulongfei.jump.dto.PaymentDTO;
import com.sulongfei.jump.dto.SendGoodsDTO;
import com.sulongfei.jump.exception.JumpException;
import com.sulongfei.jump.mapper.*;
import com.sulongfei.jump.model.*;
import com.sulongfei.jump.response.ChargeListRes;
import com.sulongfei.jump.response.PaymentOrderRes;
import com.sulongfei.jump.response.Response;
import com.sulongfei.jump.rest.request.SendGoodsRequest;
import com.sulongfei.jump.rest.response.BaseResponse;
import com.sulongfei.jump.rest.response.RestResponse;
import com.sulongfei.jump.service.ChargeService;
import com.sulongfei.jump.utils.ExcelUtil;
import com.sulongfei.jump.web.interceptor.UserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/6/12 17:12
 * @Version 1.0
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class ChargeServiceImpl implements ChargeService {

    @Autowired
    private SendGoodsMapper sendGoodsMapper;
    @Autowired
    private PaymentOrderMapper orderMapper;
    @Autowired
    private SecurityUserMapper userMapper;
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private RestService restService;
    @Autowired
    private TicketMapper ticketMapper;

    @Override
    public Response chargeList(BaseDTO dto) throws IOException {
        List<ChargeListRes> list = ExcelUtil.readChargeXLSX();
        List<ChargeListRes> data = Lists.newArrayList();
        list.forEach(charge -> {
            if (dto.getRemoteClubId().equals(charge.getRemoteClubId())) {
                Goods goods = goodsMapper.selectByGoodsId(charge.getGoodsId());
                charge.setGoodsPicture(goods.getGoodsPicture());
                charge.setGoodsText(goods.getGoodsText());
                data.add(charge);
            }
        });
        return new Response(data);
    }

    @Override
    @Transactional(readOnly = false)
    public Response sendGoods(SendGoodsDTO dto) {
        SendGoods sendGoods = sendGoodsMapper.selectByPrimaryKey(dto.getId());
        SecurityUser user = UserInterceptor.getLocalUser();

        sendGoods.setSendPerson(dto.getSendPerson());
        sendGoods.setMobile(user.getPhoneNumber());
        sendGoods.setSendPlace(StrUtil.join("-", dto.getProvince(), dto.getCity(), dto.getDistrict(), dto.getReceiverAddress()));
        sendGoods.setStatus((byte) 1);

        user.setReceiverName(dto.getSendPerson());
        user.setProvince(dto.getProvince());
        user.setCity(dto.getCity());
        user.setDistrict(dto.getDistrict());
        user.setReceiverAddress(dto.getReceiverAddress());
        userMapper.updateByPrimaryKey(user);

        SendGoodsRequest request = new SendGoodsRequest(
                user.getMemberId(),
                sendGoods.getOrgId(),
                sendGoods.getGoodsId(),
                sendGoods.getGoodsNum(),
                sendGoods.getSaleId(),
                sendGoods.getSaleType(),
                sendGoods.getSendPerson(),
                sendGoods.getMobile(),
                sendGoods.getSendPlace(),
                sendGoods.getOrderId()
        );

        ResponseEntity<RestResponse<BaseResponse>> result = restService.createSendOrder(request);
        if (HttpStatus.OK.equals(result.getStatusCode()) && "200".equals(result.getBody().getErrorCode())) {
            sendGoodsMapper.updateByPrimaryKey(sendGoods);
        }
        return new Response();
    }

    @Override
    @Transactional(readOnly = false)
    public Response advancePayment(PaymentDTO paymentDTO) {
        PaymentOrder order = orderMapper.selectByOrderId(paymentDTO.getSwOrderId());
        if (order == null){
            order = new PaymentOrder();
            order.setUserId(UserInterceptor.getLocalUser().getId());
            order.setOrgId(paymentDTO.getRemoteClubId());
            order.setProductId(paymentDTO.getProductId());
            order.setProductNum(paymentDTO.getNum());
            order.setPrice(paymentDTO.getPrice());
            order.setTicketNum(paymentDTO.getTicketNum());
            order.setBuyTime(new Timestamp(paymentDTO.getBuyTime()));
            order.setSwOrderId(paymentDTO.getSwOrderId());
            order.setResult(paymentDTO.getResult());
            order.setStatus(paymentDTO.getStatus());
            orderMapper.insertSelective(order);
        }else {
            order.setResult(paymentDTO.getResult());
            orderMapper.updateByPrimaryKey(order);
        }
        // 赠送门票
        if (order.getResult() == 1) {
            Ticket ticket = ticketMapper.selectByClubId(order.getUserId(), order.getOrgId());
            ticket.setNum(ticket.getNum() + order.getTicketNum());
            ticketMapper.updateByPrimaryKey(ticket);
        }
        return new Response();
    }

    @Override
    @Transactional(readOnly = false)
    public Response orderCallBack(Long swOrderId, Byte status) {
        PaymentOrder order = orderMapper.selectByOrderId(swOrderId);
        if (order == null) throw new JumpException(ResponseStatus.NO_ORDER);
        order.setStatus(status);
        orderMapper.updateByPrimaryKey(order);
        return new Response();
    }

    @Override
    public Response orderList(BaseDTO dto) {
        List<PaymentOrder> list = orderMapper.selectAll(UserInterceptor.getLocalUser().getId(), dto.getRemoteClubId());
        List<PaymentOrderRes> data = Lists.newArrayList();
        list.forEach(order -> {
            PaymentOrderRes res = new PaymentOrderRes();
            res.setProductNum(order.getProductNum());
            res.setPrice(order.getPrice());
            res.setSwOrderId(order.getSwOrderId());
            res.setBuyTime(order.getBuyTime().getTime());
            res.setName(order.getGoods().getGoodsName());
            res.setLogo(order.getGoods().getGoodsPicture());
            res.setResult(order.getResult());
            res.setStatus(order.getStatus());
            data.add(res);
        });
        return new Response(data);
    }
}
