package com.sulongfei.jump.response;

import com.sulongfei.jump.constants.ResponseStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 数据响应类
 *
 * @param <T>
 * @author siguiyang
 */
@ApiModel(value = "返回对象")
@Data
public class Response<T> implements Serializable {

    private static final long serialVersionUID = 473372815866107289L;
    /**
     * 数据响应吗
     */
    @ApiModelProperty(value = "响应码")
    private int code = ResponseStatus.Code.SUCCESS;
    /**
     * 响应消息
     */
    @ApiModelProperty(value = "响应消息")
    private String msg = ResponseStatus.SUCCESS_MSG;
    /**
     * 响应时间
     */
    @ApiModelProperty(value = "响应时间")
    private long timestamp = System.currentTimeMillis();
    /**
     * 响应数据
     */
    @ApiModelProperty(value = "响应数据对象")
    private T data;


    public Response() {
    }

    public Response(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Response(T data) {
        this.data = data;
    }

}
