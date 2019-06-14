package com.sulongfei.jump.constants;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/5/29 17:09
 * @Version 1.0
 */
public interface Constants {

    interface Delete {
        Boolean NO = false;
        Boolean YES = true;
    }

    interface RedisName {
        String LOGIN_SMS_CODE = "login_sms_code:";
        String LAST_WEEK_RANK = "last_week_rank:";
    }
}
