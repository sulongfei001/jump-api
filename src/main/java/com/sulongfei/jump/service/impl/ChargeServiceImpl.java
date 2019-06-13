package com.sulongfei.jump.service.impl;

import com.sulongfei.jump.dto.BaseDTO;
import com.sulongfei.jump.response.ChargeListRes;
import com.sulongfei.jump.response.Response;
import com.sulongfei.jump.service.ChargeService;
import com.sulongfei.jump.utils.ExcelUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/6/12 17:12
 * @Version 1.0
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class ChargeServiceImpl implements ChargeService {

    @Override
    public Response chargeList(BaseDTO dto) throws IOException {
        List<ChargeListRes> list = ExcelUtil.readChargeXLSX();
        return new Response(list);
    }
}
