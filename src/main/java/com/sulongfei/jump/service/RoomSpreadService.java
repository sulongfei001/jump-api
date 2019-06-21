package com.sulongfei.jump.service;

import com.sulongfei.jump.dto.BaseDTO;
import com.sulongfei.jump.dto.RoomSpreadDTO;
import com.sulongfei.jump.dto.SettleDTO;
import com.sulongfei.jump.response.Response;

import java.io.IOException;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/6/20 14:46
 * @Version 1.0
 */
public interface RoomSpreadService {
    Response spreadRoomCreate(RoomSpreadDTO dto);

    Response spreadRoomList(BaseDTO dto);

    Response settleSpreadGame(SettleDTO dto) throws IOException;

    Response spreadRoomGet(BaseDTO dto, String password) throws IOException;
}
