package com.sulongfei.jump.utils;

import java.io.File;
import java.io.InputStream;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/6/28 14:16
 * @Version 1.0
 */
public class QCloudUpload {
    /**
     * 数据流文件上传
     *
     * @param is       输入流
     * @param fileName 上传文件名
     * @return 文件全路径
     */
    public static String uploadToStream(InputStream is, String fileName) {
        return QCloudConfiguration.uploadStream(is, fileName);
    }

    public static String uploadToFile(File file, String fileName) {
        return QCloudConfiguration.uploadFile(file, fileName);
    }
}
