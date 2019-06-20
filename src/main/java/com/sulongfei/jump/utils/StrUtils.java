package com.sulongfei.jump.utils;


import java.math.BigDecimal;
import java.util.Random;

/**
 * Created by lance on 16-10-20.
 */
public class StrUtils {

    /**
     * 随机生成数字
     *
     * @param length
     * @return
     */
    public static String randomNumber(int length) {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        sb.append(random.nextInt(9) + 1);
        for (int i = 0; i < length - 1; ++i) {
            int number = random.nextInt(10);
            sb.append(number);
        }
        return sb.toString();
    }

    public static BigDecimal getGoodsPrice(BigDecimal price,Integer premiumProportion){
        final int DEF_DIV_SCALE = 10;
        BigDecimal premium = BigDecimal.valueOf(premiumProportion).divide(BigDecimal.valueOf(100), DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP);
        return price.multiply(premium);
    }

}
