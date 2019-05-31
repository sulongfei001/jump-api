package com.sulongfei.jump.model;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * model 基类
 *
 * @author siguiyang
 */
@Data
public class Model implements Serializable {
    private static final long serialVersionUID = 4400997987429664604L;

    /**
     * 数据库主键
     */
    private Long id;
    /**
     * 数据库记录创建时间
     */
    private Timestamp createTime;
    /**
     * 数据库记录发生更新的时间
     */
    private Timestamp updateTime;
    /**
     * 数据库删除标志<br />
     * 1: 删除
     * 0: 未删除
     */
    private Boolean deleteStatus;
}
