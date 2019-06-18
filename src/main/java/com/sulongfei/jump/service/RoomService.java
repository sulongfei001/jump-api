package com.sulongfei.jump.service;

import com.sulongfei.jump.dto.BaseDTO;
import com.sulongfei.jump.dto.RoomSpreadDTO;
import com.sulongfei.jump.dto.SettleDTO;
import com.sulongfei.jump.response.Response;

import java.io.IOException;
import java.util.List;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/5/30 14:33
 * @Version 1.0
 */
public interface RoomService {
    Response roomSimpleList(BaseDTO dto);

    Response settleSimpleGame(SettleDTO dto) throws IOException;

    Response rankList(BaseDTO dto);

    Response spreadRoomCreate(RoomSpreadDTO dto);

    Response spreadRoomList(BaseDTO dto);

    Response settleSpreadGame(SettleDTO dto) throws IOException;

    Response spreadRoomGet(BaseDTO dto, String password) throws IOException;

    Response roomSimpleGet(BaseDTO dto, Long roomId) throws IOException;

}
