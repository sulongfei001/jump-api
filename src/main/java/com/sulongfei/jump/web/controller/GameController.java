package com.sulongfei.jump.web.controller;

import com.sulongfei.jump.dto.BaseDTO;
import com.sulongfei.jump.response.PrdListRes;
import com.sulongfei.jump.response.RandomResultRes;
import com.sulongfei.jump.response.Response;
import com.sulongfei.jump.service.GameService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/6/6 16:28
 * @Version 1.0
 */
@Api(tags = "游戏操作")
@RestController
@RequestMapping("/{remoteClubId}/{saleId}/{saleType}/game")
public class GameController extends BaseController {

    @Autowired
    private GameService gameService;

    @ApiOperation(value = "随机结果", notes = "随机获取该酒吧一个结果")
    @PostMapping("/random_game_result")
    public Response<RandomResultRes> randomGameResult(
            @ApiParam(value = "基础请求数据", hidden = true) BaseDTO dto
    ) {
        verifyBaseDTO(dto);
        return gameService.randomGameResult(dto);
    }

    @ApiOperation(value = "卡券列表", notes = "获取该用户所有卡券")
    @PostMapping("/prdlist")
    public Response<PrdListRes> getPrdList(
            @ApiParam(value = "基础请求数据", hidden = true) BaseDTO dto
    ) {
        verifyBaseDTO(dto);
        return gameService.getPrdList(dto);
    }

}
