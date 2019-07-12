package com.sulongfei.jump.exception;

import com.sulongfei.jump.constants.ResponseStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class JumpException extends RuntimeException {
    private static final long serialVersionUID = -2013396565671482319L;

    private int code = ResponseStatus.Code.FAILURE;

    public JumpException(String message) {
        super(message);
    }

    public JumpException(int code,String message){
        super(message);
        this.code = code;
    }
}
