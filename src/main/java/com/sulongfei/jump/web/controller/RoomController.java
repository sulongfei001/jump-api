package com.sulongfei.jump.web.controller;

import com.sulongfei.jump.constants.ResponseStatus;
import com.sulongfei.jump.dto.BaseDTO;
import com.sulongfei.jump.dto.SettleDTO;
import com.sulongfei.jump.response.Response;
import com.sulongfei.jump.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/5/30 14:28
 * @Version 1.0
 */
@RestController
@RequestMapping("/room")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @PostMapping("/simple/list")
    public Response roomSimpleList(@RequestBody BaseDTO dto) {
        Assert.notNull(dto.getRemoteClubId(), ResponseStatus.EMPTY_CLUD_ID);
        return roomService.roomSimpleList(dto);
    }

    @PostMapping("/simple/settle")
    public Response settleSimpleGame(@RequestBody SettleDTO dto) {
        Assert.notNull(dto.getRemoteClubId(), ResponseStatus.EMPTY_CLUD_ID);
        Assert.notNull(dto.getSaleId(), ResponseStatus.EMPTY_SALE);
        Assert.notNull(dto.getSaleType(), ResponseStatus.EMPTY_SALE);
        Assert.notNull(dto.getConsumeTicket(), ResponseStatus.EMPTY_TICKET);
        Assert.notNull(dto.getGetTicket(), ResponseStatus.EMPTY_TICKET);
        Assert.notNull(dto.getIntegral(), ResponseStatus.ERROR_INTEGRAL);
        return roomService.settleSimpleGame(dto);
    }

    @PostMapping("/rank_list")
    public Response rankList(@RequestBody BaseDTO dto) {
        Assert.notNull(dto.getRemoteClubId(), ResponseStatus.EMPTY_CLUD_ID);
        return roomService.rankList(dto);
    }

}
