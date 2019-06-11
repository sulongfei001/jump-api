package com.sulongfei.jump.utils;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.ObjectMetadata;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * 阿里云存储配置
 */
public class UploadConfiguration {

    private static String ACCESS_KEY_ID;
    private static String ACCESS_KEY_SECRET;
    private static String ENDPOINT;
    private static String BUCKET_NAME;
    private static String FOLDER_IMAGE;

    static {
        ENDPOINT = OSSClientConstants.ENDPOINT;
        ACCESS_KEY_ID = OSSClientConstants.ACCESS_KEY_ID;
        ACCESS_KEY_SECRET = OSSClientConstants.ACCESS_KEY_SECRET;
        BUCKET_NAME = OSSClientConstants.BUCKET_NAME;
        FOLDER_IMAGE = OSSClientConstants.FOLDER_IMAGE;
    }

    /**
     * 获取阿里云OSS客户端对象
     *
     * @return ossClient
     */
    public static OSSClient getOSSClient() {
        return new OSSClient(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
    }

    /**
     * 批量上传图片
     *
     * @param is
     * @return
     * @throws IOException
     */
    public static String uploadStream(InputStream is, String fileName) throws IOException {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(is.available());
        metadata.setContentEncoding("utf-8");
        metadata.setContentType(getContentType(fileName));
        metadata.setContentDisposition("filename/filesize=" + fileName + "/" + is.available() + "Byte.");
        String objectName = FOLDER_IMAGE + "/" + DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN) + "/" + fileName;

        OSSClient ossClient = null;
        try {
            ossClient = getOSSClient();
            ossClient.putObject(BUCKET_NAME, objectName, is, metadata);
        } catch (OSSException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            ossClient.shutdown();
        }
        return "http://" + BUCKET_NAME + "." + ENDPOINT + "/" + objectName;
    }

    /**
     * 通过文件名判断并获取OSS服务文件上传时文件的contentType
     *
     * @param fileName 文件名
     * @return 文件的contentType
     */
    public static String getContentType(String fileName) {
        //文件的后缀名
        String fileExtension = fileName.substring(fileName.lastIndexOf("."));
        if (".bmp".equalsIgnoreCase(fileExtension)) {
            return "image/bmp";
        }
        if (".gif".equalsIgnoreCase(fileExtension)) {
            return "image/gif";
        }
        if (".jpeg".equalsIgnoreCase(fileExtension) || ".jpg".equalsIgnoreCase(fileExtension) || ".png".equalsIgnoreCase(fileExtension)) {
            return "image/jpeg";
        }
        if (".html".equalsIgnoreCase(fileExtension)) {
            return "text/html";
        }
        if (".txt".equalsIgnoreCase(fileExtension)) {
            return "text/plain";
        }
        if (".vsd".equalsIgnoreCase(fileExtension)) {
            return "application/vnd.visio";
        }
        if (".ppt".equalsIgnoreCase(fileExtension) || "pptx".equalsIgnoreCase(fileExtension)) {
            return "application/vnd.ms-powerpoint";
        }
        if (".doc".equalsIgnoreCase(fileExtension) || "docx".equalsIgnoreCase(fileExtension)) {
            return "application/msword";
        }
        if (".xml".equalsIgnoreCase(fileExtension)) {
            return "text/xml";
        }
        //默认返回类型
        return "image/jpeg";
    }

}
