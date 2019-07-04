package com.sulongfei.jump.utils;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpMethodName;
import com.qcloud.cos.model.GeneratePresignedUrlRequest;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;


/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/6/28 14:17
 * @Version 1.0
 */
public class QCloudConfiguration {
    private static final String secretId = "AKIDnbUJSPfXkmhT2SsZEm7HC0R6ZmkT4XO5";
    private static final String secretKey = "qU6YmhT3a4SbXHpyvlSxKZjkbFaprjTz";
    private static final String bucketName = "jump-images-1259462541";
    private static final String regionName = "ap-shanghai";
    private static final COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
    private static final ClientConfig clientConfig = new ClientConfig(new Region(regionName));
    private static final COSClient cosClient = new COSClient(cred, clientConfig);

    /**
     * 上传到COS服务器 如果同名文件会覆盖服务器上的
     *
     * @param stream   文件流
     * @param fileName 文件名称 包括后缀名
     * @return 出错返回"" ,唯一MD5数字签名
     */
    public static String uploadStream(InputStream stream, String fileName) {
        try {
            // 创建上传Object的Metadata
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(stream.available());
            objectMetadata.setCacheControl("no-cache");
            objectMetadata.setHeader("Pragma", "no-cache");
            objectMetadata.setContentType(getcontentType(fileName));
            objectMetadata.setContentDisposition("inline;filename=" + fileName);
            String key = DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN) + "/" + fileName;
            PutObjectRequest putRequest = new PutObjectRequest(bucketName, key, stream, objectMetadata);
            // 上传文件
            cosClient.putObject(putRequest);
            return key;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String uploadFile(File file, String fileName) {
        String key = DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN) + "/" + fileName;
        PutObjectRequest putRequest = new PutObjectRequest(bucketName, key, file);
        PutObjectResult res = cosClient.putObject(putRequest);
        return key;
    }

    /**
     * 获得预签名URL
     *
     * @param key
     * @return
     */
    public static String getSignUrl(String key) {
        Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 10);
        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, key, HttpMethodName.GET);
        request.setExpiration(expiration);
        // 生成URL
        URL url = cosClient.generatePresignedUrl(request);
        return url == null ? "" : url.toString();
    }

    public static String getUrl(String key) {
        return "https://" + bucketName + ".cos." + regionName + ".myqcloud.com/" + key;
    }


    public static String getcontentType(String fileName) {
        //文件的后缀名
        String filenameExtension = fileName.substring(fileName.lastIndexOf("."));
        if (filenameExtension.equalsIgnoreCase("bmp")) {
            return "image/bmp";
        }
        if (filenameExtension.equalsIgnoreCase("gif")) {
            return "image/gif";
        }
        if (filenameExtension.equalsIgnoreCase("jpeg") || filenameExtension.equalsIgnoreCase("jpg")
                || filenameExtension.equalsIgnoreCase("png")) {
            return "image/jpeg";
        }
        if (filenameExtension.equalsIgnoreCase("html")) {
            return "text/html";
        }
        if (filenameExtension.equalsIgnoreCase("txt")) {
            return "text/plain";
        }
        if (filenameExtension.equalsIgnoreCase("vsd")) {
            return "application/vnd.visio";
        }
        if (filenameExtension.equalsIgnoreCase("pptx") || filenameExtension.equalsIgnoreCase("ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (filenameExtension.equalsIgnoreCase("docx") || filenameExtension.equalsIgnoreCase("doc")) {
            return "application/msword";
        }
        if (filenameExtension.equalsIgnoreCase("xml")) {
            return "text/xml";
        }
        return "image/jpeg";
    }
}
