package com.sulongfei.jump.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

@EqualsAndHashCode(callSuper = true)
@Data
public class Club extends Model {

    private static final long serialVersionUID = 997627662628618997L;

    private Long id;

    private Long remoteClubId;

    private Long supplierId;

    private String supplierName;

    private String supplierAddress;

    private String companyName;

    private String phone;

    private Byte isOrg;

    private Byte status;

    private Timestamp createTime;

    private Timestamp lastUpdateTime;


}