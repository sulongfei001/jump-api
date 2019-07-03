package com.sulongfei.jump.service;

import com.sulongfei.jump.model.SendGoods;
import com.sulongfei.jump.rest.request.SendPrdRequest;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/6/11 14:54
 * @Version 1.0
 */
public interface TaskService {
    void resetRank();

    void savePrd(SendGoods sendGoods);

    void sendPrd(SendPrdRequest request);

    void settle();

}
