package com.sulongfei.jump.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class SecurityUser extends Model {

    private static final long serialVersionUID = 4773591935821999048L;

    private Long memberId;

    private Boolean isSaler;

    private String phoneNumber;

    private String password;

    private String avatar;

    private String nickname;

    private Boolean gender;

    private Long registerClue;

    private Timestamp lastOperationTime;

    private Long lastOperationClub;

    private String receiverName;

    private String receiverAddress;

    private Boolean everydayTicket;

    private Integer ticketNum;

}