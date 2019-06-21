package com.sulongfei.jump.web.controller;

import com.sulongfei.jump.dto.BaseDTO;
import com.sulongfei.jump.dto.RoomSpreadDTO;
import com.sulongfei.jump.dto.SettleDTO;
import com.sulongfei.jump.response.*;
import com.sulongfei.jump.service.RoomSimpleService;
import com.sulongfei.jump.service.RoomSpreadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/5/30 14:28
 * @Version 1.0
 */
@Api(tags = "房间操作")
@RestController
@RequestMapping("/{remoteClubId}/{saleId}/{saleType}/room")
public class RoomController extends BaseController {
    @Autowired
    private RoomSimpleService simpleService;
    @Autowired
    private RoomSpreadService spreadService;

    @ApiOperation(value = "获取游戏方房间列表")
    @PostMapping("/simple/list")
    public Response<List<RoomSimpleRes>> roomSimpleList(
            @ApiParam(value = "基础请求数据", hidden = true) BaseDTO dto
    ) {
        verifyBaseDTO(dto);
        return simpleService.roomSimpleList(dto);
    }

    @ApiOperation(value = "获取大游戏房间")
    @ApiImplicitParam(value = "房间ID", name = "roomId", paramType = "path", required = true)
    @PostMapping("/simple/get/{roomId}")
    public Response<RoomSimpleRes> roomSimpleGet(
            @ApiParam(value = "基础请求数据", hidden = true) BaseDTO dto,
            @PathVariable Long roomId
    ) throws IOException {
        return simpleService.roomSimpleGet(dto, roomId);
    }

    @ApiOperation(value = "游戏房间游戏结算", notes = "用于游戏方房间结算分数")
    @PostMapping("/simple/settle")
    public Response<SettleRes> settleSimpleGame(
            @ApiParam(value = "基础请求数据", hidden = true) BaseDTO baseDTO,
            @ApiParam(value = "Json消息体", required = true) @RequestBody SettleDTO dto
    ) throws IOException {
        BeanUtils.copyProperties(baseDTO, dto);
        verifySettleSimpleDTO(dto);
        return simpleService.settleSimpleGame(dto);
    }

    @ApiOperation(value = "推广员创建房间")
    @PostMapping("/spread/create")
    public Response spreadRoomCreate(
            @ApiParam(value = "基础请求数据", hidden = true) BaseDTO baseDTO,
            @ApiParam(value = "Json消息体", required = true) @RequestBody RoomSpreadDTO dto
    ) {
        BeanUtils.copyProperties(baseDTO, dto);
        verifySpreadRoomCreateDTO(dto);
        return spreadService.spreadRoomCreate(dto);
    }

    @ApiOperation(value = "根据密码获取推广员房间")
    @ApiImplicitParam(value = "房间密码", name = "password", paramType = "path", required = true)
    @PostMapping("/spread/get/{password}")
    public Response spreadRoomGet(
            @ApiParam(value = "基础请求数据", hidden = true) BaseDTO dto,
            @PathVariable String password
    ) throws IOException {
        return spreadService.spreadRoomGet(dto, password);
    }

    @ApiOperation(value = "推广员房间列表")
    @PostMapping("/spread/list")
    public Response<List<RoomSpreadRes>> spreadRoomList(
            @ApiParam(value = "基础请求数据", hidden = true) BaseDTO dto
    ) {
        verifyBaseDTO(dto);
        return spreadService.spreadRoomList(dto);
    }

    @ApiOperation(value = "推广房间游戏结算", notes = "用于推广方房间结算分数")
    @PostMapping("/spread/settle")
    public Response<SettleRes> settleSpreadGame(
            @ApiParam(value = "基础请求数据", hidden = true) BaseDTO baseDTO,
            @ApiParam(value = "Json消息体", required = true) @RequestBody SettleDTO dto
    ) throws IOException {
        BeanUtils.copyProperties(baseDTO, dto);
        verifySettleSimpleDTO(dto);
        return spreadService.settleSpreadGame(dto);
    }


    @ApiOperation(value = "本门店排行榜")
    @PostMapping("/rank_list")
    public Response<RankListRes> rankList(
            @ApiParam(value = "基础请求数据", hidden = true) BaseDTO dto
    ) {
        verifyBaseDTO(dto);
        return simpleService.rankList(dto);
    }

}
