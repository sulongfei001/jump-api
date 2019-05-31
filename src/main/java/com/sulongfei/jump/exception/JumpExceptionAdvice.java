package com.sulongfei.jump.exception;

import com.sulongfei.jump.constants.ResponseStatus;
import com.sulongfei.jump.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 统一异常处理机制
 *
 * @author siguiyang
 */
@RestControllerAdvice
@Slf4j
public class JumpExceptionAdvice {

    @ExceptionHandler
    public Object doException(Exception e) {
        log.error("统一异常处理机制，触发异常 msg = {}", e.getMessage());
        e.printStackTrace();
        if (e instanceof JumpException) {
            JumpException exception = (JumpException) e;
            return new Response<>(exception.getCode(), exception.getMessage());
        } else if (e instanceof IllegalArgumentException) {
            return new Response<>(ResponseStatus.Code.ILLEGAL_ARGUMENT, e.getMessage());
        } else if (e instanceof AccessDeniedException) {
            return new Response<>(ResponseStatus.Code.NO_PERMISSION, ResponseStatus.NO_PERMISSION);
        }
        return new Response<>(ResponseStatus.Code.OTHER_EXCEPTION, ResponseStatus.OTHER_EXCEPTION);
    }
}
