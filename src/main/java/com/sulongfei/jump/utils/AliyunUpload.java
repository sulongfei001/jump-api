package com.sulongfei.jump.utils;

import java.io.IOException;
import java.io.InputStream;

/**
 * 阿里云文件上传
 */
public class AliyunUpload {

    /**
     * 数据流文件上传
     *
     * @param is       输入流
     * @param fileName 上传文件名
     * @return 文件全路径
     */
    public static String uploadToStream(InputStream is, String fileName) throws IOException {
        return UploadConfiguration.uploadStream(is, fileName);
    }

}
