package com.sulongfei.jump.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

@EqualsAndHashCode(callSuper = true)
@Data
public class SecurityUser extends Model {

    private static final long serialVersionUID = 4773591935821999048L;
    private Long id;

    private Long memberId;

    private Boolean isSaler;

    private String phoneNumber;

    private String password;

    private String avatar;

    private String nickname;

    private Byte gender;

    private Long registerClue;

    private Timestamp lastOperationTime;

    private Long lastOperationClub;

    private String receiverName;

    private String province;

    private String city;

    private String district;

    private String receiverAddress;

    private Boolean everydayTicket;

    private Integer ticketNum;

}