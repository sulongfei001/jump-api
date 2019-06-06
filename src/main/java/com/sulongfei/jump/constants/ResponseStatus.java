package com.sulongfei.jump.constants;

/**
 * 响应码<br />
 * <p>
 *
 * @author siguiyang
 */
public interface ResponseStatus {

    // 响应码
    interface Code {
        // 错误码
        int FAILURE = 1000;
        // 没有权限码
        int NO_PERMISSION = 2000;
        // 非法参数异常码
        int ILLEGAL_ARGUMENT = 3000;
        // 其他异常码
        int OTHER_EXCEPTION = 4000;
        // 成功码
        int SUCCESS = 200;

    }

    String SUCCESS_MSG = "请求成功";

    String NO_PERMISSION = "您没有权限访问";

    String OTHER_EXCEPTION = "网络出了点小问题";

    String TELNET_EXCEPTION = "网络连接错误，请稍后重试";

    String REPEAT_SUBMIT = "请勿重复提交";

    String USER_PHONE_NOT_EXISTS = "用户不存在";

    String USER_PHONE_EXCEPTION = "用户账号异常，请联系客服！";

    String USER_ACCOUNT_PASSWORD_NOT_CORRECT = "账号或密码不正确";

    String USER_PHONE_REGISTERED = "此号码已注册";

    String USER_GRAPHIC_CODE_EMPTY = "图形验证码不能为空";

    String SMS_CODE_NOT_EMPTY = "短信验证码不能为空";


    String SMS_CODE_EXPIRE = "短信验证码已过期";

    String SMS_CODE_ERROR = "短信验证码不正确";

    String LOGIN_EXPIRE = "登陆过期，请重新登陆";

    String GOODS_EXPIRE = "商品已失效";

    String SERVICE_NOT_FOUND = "未找到服务地址";

    String PUBLISH_COUPON_PHONE_IS_BLANK = "发送优惠券手机号码为空";

    String EMPTY_PHONE_NUMBER = "手机号码为空";

    String EMPTY_CLUB_ID = "门店为空";

    String EMPTY_SALE = "推广员为空";

    String EMPTY_TICKET = "门票数量异常";

    String EMPTY_ROOM_ID = "房间号为空";

    String EMPTY_SPREAD_GOODS = "推广商品为空";

    String EMPTY_JOIN_NUM = "参与人数为空";

    String ERROR_INTEGRAL = "分数异常";

    String NO_ENOUGH_TICKET = "没有足够的门票";

    String NO_ENOUGH_GOODS = "剩余库存不足";


}
