package com.sulongfei.jump.web.controller;

import com.sulongfei.jump.constants.ResponseStatus;
import com.sulongfei.jump.dto.*;
import org.springframework.util.Assert;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/6/3 18:38
 * @Version 1.0
 */
public class BaseController {
    protected void verifySmsCodeDTO(UserLoginDTO dto) {
        verifyBaseDTO(dto);
        Assert.notNull(dto.getPhoneNumber(), ResponseStatus.EMPTY_PHONE_NUMBER);
    }

    protected void verifySettleSimpleDTO(SettleDTO dto) {
        verifyBaseDTO(dto);
        Assert.notNull(dto.getRoomId(), ResponseStatus.EMPTY_ROOM_ID);
    }

    protected void verifySpreadRoomCreateDTO(RoomSpreadDTO dto) {
        verifyBaseDTO(dto);
        Assert.notNull(dto.getSpreadGoodsId(), ResponseStatus.EMPTY_SPREAD_GOODS);
        Assert.notNull(dto.getJoinNum(), ResponseStatus.EMPTY_JOIN_NUM);
        Assert.notNull(dto.getTicketNum(), ResponseStatus.EMPTY_TICKET);
    }

    protected void verifyBaseDTO(BaseDTO dto) {
        Assert.notNull(dto.getRemoteClubId(), ResponseStatus.EMPTY_CLUB_ID);
        Assert.notNull(dto.getSaleId(), ResponseStatus.EMPTY_SALE);
        Assert.notNull(dto.getSaleType(), ResponseStatus.EMPTY_SALE);
    }

    protected void verifyPaymentDTO(PaymentDTO dto) {
        verifyBaseDTO(dto);
        Assert.notNull(dto.getProductId(), "商品ID为空");
        Assert.notNull(dto.getNum(), "商品数量为空");
        Assert.notNull(dto.getSwOrderId(), "订单号为空");
        Assert.notNull(dto.getResult(), "支付状态为空");
        Assert.notNull(dto.getStatus(), "卡券状态为空");
    }
}
