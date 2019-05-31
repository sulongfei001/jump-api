package com.sulongfei.jump.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Integral extends Model {
    private Long id;

    private Long userId;

    private Long remoteClubId;

    private Integer integral;

    private SecurityUser user;

    public Integral() {
    }

    public Integral(Long userId, Long remoteClubId, Integer integral) {
        this.userId = userId;
        this.remoteClubId = remoteClubId;
        this.integral = integral;
    }
}