package com.sulongfei.jump.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Ticket extends Model {
    private Long id;

    private Long userId;

    private Long remoteClubId;

    private Integer num;

    public Ticket() {
    }

    public Ticket(Long userId, Long remoteClubId, Integer num) {
        this.userId = userId;
        this.remoteClubId = remoteClubId;
        this.num = num;
    }
}