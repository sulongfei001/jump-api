package com.sulongfei.jump.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class GlobalDictionary extends Model {
    private Long id;

    private String key;

    private String value;

    private String remark;

    private Byte type;

}