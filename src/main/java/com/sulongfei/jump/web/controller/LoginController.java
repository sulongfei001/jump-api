package com.sulongfei.jump.web.controller;

import com.sulongfei.jump.constants.ResponseStatus;
import com.sulongfei.jump.dto.UserLoginDTO;
import com.sulongfei.jump.response.Response;
import com.sulongfei.jump.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
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
 * @Date 2019/5/29 16:44
 * @Version 1.0
 */
@Api("用户登录")
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @ApiOperation("获取验证码")
    @PostMapping("/sms_code_generating")
    public Response smsCodeGenerating(@RequestBody UserLoginDTO dto) {
        verifyDTO(dto);
        return loginService.generatingSmsCode(dto);
    }

    private void verifyDTO(UserLoginDTO dto) {
        Assert.notNull(dto.getPhoneNumber(), ResponseStatus.EMPTY_PHONE_NUMBER);
        Assert.notNull(dto.getRemoteClubId(), ResponseStatus.EMPTY_CLUD_ID);
        Assert.notNull(dto.getSaleId(), ResponseStatus.EMPTY_SALE);
        Assert.notNull(dto.getSaleType(), ResponseStatus.EMPTY_SALE);
    }
}
