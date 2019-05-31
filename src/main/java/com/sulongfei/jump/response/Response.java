package com.sulongfei.jump.response;

import com.sulongfei.jump.constants.ResponseStatus;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 数据响应类
 *
 * @param <T>
 * @author siguiyang
 */
@Data
public class Response<T> implements Serializable {

    private static final long serialVersionUID = 473372815866107289L;
    /**
     * 数据响应吗
     */
    private int code = ResponseStatus.Code.SUCCESS;
    /**
     * 响应消息
     */
    private String msg = ResponseStatus.SUCCESS_MSG;
    /**
     * 响应时间
     */
    private long timestamp = System.currentTimeMillis();
    /**
     * 响应数据
     */
    private T data;
    /**
     * 分页总数
     */
    private long total;

    public Response() {
    }

    public Response(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Response(T data) {
        this.data = data;
    }

    public static <T> Response<List<T>> toResponse(List<T> data, long total) {
        Response<List<T>> response = new Response<>();
        response.setTotal(total);
        response.setData(data);
        return response;
    }

}
