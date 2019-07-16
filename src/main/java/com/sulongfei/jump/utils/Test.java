package com.sulongfei.jump.utils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/6/4 11:30
 * @Version 1.0
 */
public class Test {


    public static void main(String[] args) throws IOException, ParseException {
//        BigDecimal price = BigDecimal.valueOf(35.22);
//        BigDecimal premium = BigDecimal.valueOf(100).divide(BigDecimal.valueOf(3),10,BigDecimal.ROUND_HALF_UP);
//        BigDecimal singlePrice = BigDecimal.valueOf(100);
//        price.multiply(premium).divide(singlePrice);
//        System.out.println(price);
//        System.out.println(premium);
//        System.out.println(singlePrice);
//        System.out.println(price.multiply(premium).divide(singlePrice,10,BigDecimal.ROUND_HALF_UP).intValue());

//        ExcelUtil.integralConfig();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(sdf.parse("2019-7-15 00:00:00").getTime());
    }
}
