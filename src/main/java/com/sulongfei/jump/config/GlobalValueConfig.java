package com.sulongfei.jump.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/5/31 14:14
 * @Version 1.0
 */
@Component
public class GlobalValueConfig {
    private static Integer smsCodeExpire;
    private static Integer entryIntegral;
    private static Integer entryNum;
    private static BigDecimal ticketSinglePrice;


    public static Integer getSmsCodeExpire() {
        return smsCodeExpire;
    }

    @Value("${sms.code.expire}")
    public void setSmsCodeExpire(Integer smsCodeExpire) {
        GlobalValueConfig.smsCodeExpire = smsCodeExpire;
    }

    public static Integer getEntryIntegral() {
        return entryIntegral;
    }

    @Value("${game.entry.integral}")
    public void setEntryIntegral(Integer entryIntegral) {
        GlobalValueConfig.entryIntegral = entryIntegral;
    }

    public static Integer getEntryNum() {
        return entryNum;
    }

    @Value("${game.entry.num}")
    public void setEntryNum(Integer entryNum) {
        GlobalValueConfig.entryNum = entryNum;
    }

    public static BigDecimal getTicketSinglePrice() {
        return ticketSinglePrice;
    }

    @Value("${ticket.single.price}")
    public void setTicketSinglePrice(BigDecimal ticketSinglePrice) {
        GlobalValueConfig.ticketSinglePrice = ticketSinglePrice;
    }
}
