package com.sulongfei.jump.utils;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;

import java.io.IOException;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/6/24 14:06
 * @Version 1.0
 */
public class QCloudUtil {
    private static Integer appid;
    private static String appkey;
    private static Integer templateId;
    private static String smsSign;

    static {
        appid = 1400223463;
        appkey = "689951b7efa9c103a8d27da90cdda448";
        templateId = 357126;
        smsSign = "考娱网络";
    }

    public static void sendSMS(String cellPhone, String smsCode) throws HTTPException, IOException {
        String[] params = {smsCode};
        SmsSingleSender sender = new SmsSingleSender(appid, appkey);
        SmsSingleSenderResult result = sender.sendWithParam("86", cellPhone, templateId, params, smsSign, "", "");
    }

    public static void main(String[] args) throws HTTPException, IOException {
        sendSMS("18848956775", "111111");
    }
}
