package com.sulongfei.jump.web.controller;

import com.sulongfei.jump.dto.BaseDTO;
import com.sulongfei.jump.response.ChargeListRes;
import com.sulongfei.jump.response.Response;
import com.sulongfei.jump.service.ChargeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/6/12 17:09
 * @Version 1.0
 */
@Api(tags = "充值操作")
@RestController
@RequestMapping("/{remoteClubId}/{saleId}/{saleType}/charge")
public class ChargeController extends BaseController {

    @Autowired
    private ChargeService chargeService;

    @PostMapping("/list")
    @ApiOperation(value = "内购商品列表")
    public Response<List<ChargeListRes>> chargeList(
            @ApiParam(value = "基础请求数据", hidden = true) BaseDTO dto
    ) throws IOException {
        verifyBaseDTO(dto);
        return chargeService.chargeList(dto);
    }
}
