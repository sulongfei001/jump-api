package com.sulongfei.jump.web.controller;

import com.sulongfei.jump.dto.BaseDTO;
import com.sulongfei.jump.dto.UserLoginDTO;
import com.sulongfei.jump.response.Response;
import com.sulongfei.jump.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/5/29 16:44
 * @Version 1.0
 */
@Api(tags = "登录操作")
@RestController
@RequestMapping("/{remoteClubId}/{saleId}/{saleType}/login")
public class LoginController extends BaseController {

    @Autowired
    private LoginService loginService;

    @ApiOperation(value = "获取验证码", notes = "输入手机号发送验证码")
    @ApiImplicitParam(name = "access_token", access = "hidden")
    @PostMapping("/sms_code_generating")
    public Response smsCodeGenerating(
            @ApiParam(value = "基础请求数据", hidden = true) BaseDTO baseDTO,
            @ApiParam(value = "Json消息体", required = true) @RequestBody UserLoginDTO dto
    ) {
        BeanUtils.copyProperties(baseDTO, dto);
        verifySmsCodeDTO(dto);
        return loginService.generatingSmsCode(dto);
    }
}
