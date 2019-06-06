package com.sulongfei.jump.web.controller;

import com.sulongfei.jump.constants.ResponseStatus;
import com.sulongfei.jump.dto.BaseDTO;
import com.sulongfei.jump.dto.RoomSpreadDTO;
import com.sulongfei.jump.dto.SettleDTO;
import com.sulongfei.jump.dto.UserLoginDTO;
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

    protected void verifySettleSimpleDTO(SettleDTO dto){
        verifyBaseDTO(dto);
        Assert.notNull(dto.getRoomId(), ResponseStatus.EMPTY_ROOM_ID);
        Assert.notNull(dto.getGetTicket(), ResponseStatus.EMPTY_TICKET);
        Assert.notNull(dto.getIntegral(), ResponseStatus.ERROR_INTEGRAL);
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
}
