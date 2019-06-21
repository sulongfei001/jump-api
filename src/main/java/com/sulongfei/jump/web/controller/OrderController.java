package com.sulongfei.jump.web.controller;

import com.sulongfei.jump.response.Response;
import com.sulongfei.jump.service.ChargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/6/20 17:56
 * @Version 1.0
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private ChargeService chargeService;

    @PostMapping("/callBack")
    public Response orderCallBack(
            @RequestParam Long swOrderId,
            @RequestParam Byte status
    ){
        return chargeService.orderCallBack(swOrderId,status);
    }

}
