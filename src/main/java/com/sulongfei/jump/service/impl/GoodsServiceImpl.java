package com.sulongfei.jump.service.impl;

import com.google.common.collect.Lists;
import com.sulongfei.jump.dto.BaseDTO;
import com.sulongfei.jump.mapper.SpreadGoodsMapper;
import com.sulongfei.jump.model.SpreadGoods;
import com.sulongfei.jump.response.Response;
import com.sulongfei.jump.response.SpreadGoodsRes;
import com.sulongfei.jump.service.GoodsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/6/3 15:01
 * @Version 1.0
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private SpreadGoodsMapper spreadGoodsMapper;

    @Override
    public Response spreadGoodsList(BaseDTO dto) {
        List<SpreadGoods> list = spreadGoodsMapper.selectByClubId(dto.getRemoteClubId());
        List<SpreadGoodsRes> data = Lists.newArrayList();
        list.forEach(spreadGoods -> {
            SpreadGoodsRes res = new SpreadGoodsRes();
            BeanUtils.copyProperties(spreadGoods, res);
            res.setRemainNum(spreadGoods.getGoods().getRemainNum());
            data.add(res);
        });
        return new Response(data);
    }
}
