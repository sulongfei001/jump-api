package com.sulongfei.jump.utils;

import cn.hutool.poi.excel.ExcelReader;
import com.google.common.collect.Lists;
import com.sulongfei.jump.response.ChargeListRes;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/6/13 11:01
 * @Version 1.0
 */
public class ExcelUtil {


    public static List<ChargeListRes> readChargeXLSX() throws IOException {
        final String FILE_PATH = "config/chargeList.xlsx";
        final Integer HEADER_ROW_INDEX = 2;
        final Integer START_ROW_INDEX = 3;
        ExcelReader reader = cn.hutool.poi.excel.ExcelUtil.getReader(new ClassPathResource(FILE_PATH).getInputStream(), 2);
        List<ChargeListRes> list = reader.read(HEADER_ROW_INDEX, START_ROW_INDEX, ChargeListRes.class);
        return list;
    }

    public static List<RandomCellGem> gameConfig() throws IOException {
        final String FILE_PATH = "config/gameConfig.xlsx";
        final Integer HEADER_ROW_INDEX = 1;
        final Integer START_ROW_INDEX = 2;
        ExcelReader reader = cn.hutool.poi.excel.ExcelUtil.getReader(new ClassPathResource(FILE_PATH).getInputStream(), 1);
        List<RandomCellGem> list = reader.read(HEADER_ROW_INDEX, START_ROW_INDEX, RandomCellGem.class);
        return list;
    }

    public static IntegralConfig integralConfig() throws IOException {
        final String FILE_PATH = "config/gameConfig.xlsx";
        final Integer HEADER_ROW_INDEX = 2;
        final Integer START_ROW_INDEX = 3;
        ExcelReader reader = cn.hutool.poi.excel.ExcelUtil.getReader(new ClassPathResource(FILE_PATH).getInputStream(), 0);
        List<IntegralConfig> list = reader.read(HEADER_ROW_INDEX, START_ROW_INDEX, IntegralConfig.class);
        return list.get(0);
    }

    public static List<Integer> getGameConfig() throws IOException {
        List<RandomCellGem> list = ExcelUtil.gameConfig();
        List<Integer> cells = Lists.newArrayList();
        list.forEach(randomCellGem -> {
            Integer gemNum = new Random().nextInt(randomCellGem.getEndGem() - randomCellGem.getStartGem() + 1) + randomCellGem.getStartGem();
            for (Integer i = 0; i < gemNum; i++) {
                Integer cellNum = new Random().nextInt(randomCellGem.getEndCell() - randomCellGem.getStartCell() + 1) + randomCellGem.getStartCell();
                if (cells.contains(cellNum)) {
                    i--;
                    continue;
                }
                cells.add(cellNum);
            }
        });
        cells.sort(Comparator.comparingInt(a -> a));
        return cells;
    }

}
