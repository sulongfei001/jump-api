package com.sulongfei.jump.service;

import com.sulongfei.jump.dto.BaseDTO;
import com.sulongfei.jump.dto.PaymentDTO;
import com.sulongfei.jump.dto.SendGoodsDTO;
import com.sulongfei.jump.response.Response;

import java.io.IOException;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/6/12 17:11
 * @Version 1.0
 */
public interface ChargeService {
    Response chargeList(BaseDTO dto) throws IOException;

    Response sendGoods(SendGoodsDTO sendGoodsDTO);

    Response advancePayment(PaymentDTO paymentDTO);

    Response orderCallBack(Long swOrderId, Byte status);

    Response orderList(BaseDTO dto);
}
