package com.sulongfei.jump.web.controller;

import com.sulongfei.jump.dto.BaseDTO;
import com.sulongfei.jump.response.Response;
import com.sulongfei.jump.response.SpreadGoodsRes;
import com.sulongfei.jump.service.GoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/6/3 14:59
 * @Version 1.0
 */
@Api(tags = "商品操作")
@RestController
@RequestMapping("/{remoteClubId}/{saleId}/{saleType}/goods")
public class GoodsController extends BaseController {
    @Autowired
    private GoodsService goodsService;

    @ApiOperation("获取推广员商品列表")
    @PostMapping("/spread/list")
    public Response<List<SpreadGoodsRes>> spreadGoodsList(
            @ApiParam(value = "基础请求数据", hidden = true) BaseDTO dto
    ) {
        verifyBaseDTO(dto);
        return goodsService.spreadGoodsList(dto);
    }

}
