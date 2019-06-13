package com.sulongfei.jump.service.impl;

import com.sulongfei.jump.mapper.IntegralMapper;
import com.sulongfei.jump.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/6/11 14:55
 * @Version 1.0
 */
@Slf4j
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class TaskServiceImpl implements TaskService {

    @Autowired
    private IntegralMapper integralMapper;

    @Override
    @Transactional(readOnly = false)
    public void resetRank() {
        integralMapper.resetRankList();
    }
}
