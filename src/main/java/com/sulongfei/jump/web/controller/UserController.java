package com.sulongfei.jump.web.controller;

import com.sulongfei.jump.dto.BaseDTO;
import com.sulongfei.jump.dto.UserDTO;
import com.sulongfei.jump.response.Response;
import com.sulongfei.jump.response.UserRes;
import com.sulongfei.jump.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/5/30 11:43
 * @Version 1.0
 */
@Api(tags = "用户操作")
@RestController
@RequestMapping("/{remoteClubId}/{saleId}/{saleType}/user")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;

    @ApiOperation("获取用户信息")
    @PostMapping("/info")
    public Response<UserRes> getUserInfo(
            @ApiParam(value = "基础请求数据", hidden = true) BaseDTO dto
    ) {
        return userService.getUserInfo();
    }

    @ApiOperation("修改用户信息")
    @PutMapping("/info/update")
    public Response updateUser(
            @ApiParam(value = "基础请求数据", hidden = true) BaseDTO baseDTO,
            @ApiParam(value = "Json消息体", required = true) @RequestBody UserDTO dto
    ) {
        BeanUtils.copyProperties(baseDTO, dto);
        verifyBaseDTO(dto);
        return userService.updateUser(dto);
    }
}
