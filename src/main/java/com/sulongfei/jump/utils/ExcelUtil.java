package com.sulongfei.jump.utils;

import cn.hutool.poi.excel.ExcelReader;
import com.sulongfei.jump.response.ChargeListRes;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.List;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/6/13 11:01
 * @Version 1.0
 */
public class ExcelUtil {
    private static String FILE_PATH = "config/chargeList.xlsx";
    private static Integer HEADER_ROW_INDEX = 2;
    private static Integer START_ROWI_NDEX = 3;

    public static List<ChargeListRes> readChargeXLSX() throws IOException {
        ExcelReader reader = cn.hutool.poi.excel.ExcelUtil.getReader(new ClassPathResource(FILE_PATH).getInputStream(), 1);
        List<ChargeListRes> list = reader.read(HEADER_ROW_INDEX, START_ROWI_NDEX, ChargeListRes.class);
        return list;
    }
}
