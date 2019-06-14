package com.sulongfei.jump.utils;

import lombok.Data;

import java.io.Serializable;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/6/14 10:51
 * @Version 1.0
 */
@Data
public class SerializeUtil<T> implements Serializable {

    private static final long serialVersionUID = -2562048503093443922L;

    private T data;

    public SerializeUtil() {
    }

    public SerializeUtil(T data) {
        this.data = data;
    }
}
