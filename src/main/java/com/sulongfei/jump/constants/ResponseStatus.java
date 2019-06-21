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

    String REPEAT_SUBMIT = "请勿重复提交";

    String USER_PHONE_NOT_EXISTS = "用户不存在";

    String SMS_CODE_EXPIRE = "短信验证码已过期";

    String EMPTY_PHONE_NUMBER = "手机号码为空";

    String EMPTY_CLUB_ID = "门店为空";

    String EMPTY_SALE = "推广员为空";

    String EMPTY_TICKET = "门票数量异常";

    String EMPTY_ROOM_ID = "房间号为空";

    String EMPTY_SPREAD_GOODS = "推广商品为空";

    String EMPTY_JOIN_NUM = "参与人数为空";

    String NO_EXIST_ROOM = "房间不存在";

    String NO_ENOUGH_TICKET = "没有足够的门票";

    String NO_ENOUGH_GOODS = "剩余库存不足";

    String GOT_TICKET = "今日已领取过门票";

    String ROOM_CLOSED = "房间已关闭";

    String NO_ORDER = "订单不存在";
}
