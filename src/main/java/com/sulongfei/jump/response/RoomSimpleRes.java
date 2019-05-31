package com.sulongfei.jump.response;

import lombok.Data;

import java.sql.Timestamp;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/5/30 14:41
 * @Version 1.0
 */
@Data
public class RoomSimpleRes {
    private Long remoteClubId;

    private Timestamp startTime;

    private Timestamp endTime;

    private Integer ticketNum;

    private GoodsRes goods;
}
