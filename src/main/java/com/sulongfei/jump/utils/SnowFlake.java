package com.sulongfei.jump.utils;

/**
 * @Description Twitter的SnowFlake算法, 使用SnowFlake算法生成一个整数，然后转化为62进制变成一个短地址URL
 * https://github.com/beyondfengyu/SnowFlake
 * @Author sulongfei
 * @Date 2019/4/12 14:29
 * @Version 1.0
 */
public class SnowFlake {
    /**
     * 起始时间戳
     */
    private final static long START_TIMESTAMP = 1555050723000L;

    /**
     * 每一部分占用的位数
     */
    private final static long SEQUENCE_BIT = 12;    //序列号占用的位数
    private final static long MACHINE_BIT = 5;  //机器标识占用的位数
    private final static long DATA_CENTER_BIT = 5;  //数据中心占用的位数

    /**
     * 每一部分的最大值
     */
    private final static long MAX_SEQUENCE = -1L ^ (-1L << SEQUENCE_BIT);
    private final static long MAX_MACHINE_NUM = -1L ^ (-1L << MACHINE_BIT);
    private final static long MAX_DATA_CENTER_NUM = -1L ^ (-1L << DATA_CENTER_BIT);

    /**
     * 每一部分向左的位移
     */
    private final static long MACHINE_LEFT = SEQUENCE_BIT;
    private final static long DATA_CENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    private final static long TIMESTAMP_LEFT = DATA_CENTER_LEFT + DATA_CENTER_BIT;

    private long dataCenterId = 1L;  //数据中心
    private long machineId = 1L; //机器标识
    private long sequence = 0L; //序列号
    private long lastTimeStamp = -1L; //上一次时间戳

    public synchronized long nextId() {
        long currTimeStamp = System.currentTimeMillis();
        if (currTimeStamp < START_TIMESTAMP) {
            throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
        }
        if (currTimeStamp == lastTimeStamp) {
            //同毫秒内，序列号自增
            sequence = (sequence + 1) & MAX_SEQUENCE;
            if (sequence == 0) {
                //同一毫秒的序列数已经达到最大
                currTimeStamp = getNextMill();
            }
        } else {
            //不同毫秒内，序列号置为0
            sequence = 0;
        }
        lastTimeStamp = currTimeStamp;
        return (currTimeStamp - START_TIMESTAMP) << TIMESTAMP_LEFT
                | dataCenterId << DATA_CENTER_LEFT
                | machineId << MACHINE_LEFT
                | sequence;
    }

    private long getNextMill() {
        long mill = System.currentTimeMillis();
        while (mill <= lastTimeStamp) {
            mill = System.currentTimeMillis();
        }
        return mill;
    }


    public static void main(String[] args) {
        SnowFlake snowFlake = new SnowFlake();
        for (int i = 0; i < (1 << 12); i++) {
            System.out.println(snowFlake.nextId());
        }
    }
}
