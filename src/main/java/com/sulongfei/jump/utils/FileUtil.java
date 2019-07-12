package com.sulongfei.jump.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/7/12 15:51
 * @Version 1.0
 */
@Slf4j
public class FileUtil {
    public static InputStream getResFileIS(String filename) throws IOException {
        Resource resource;
        File file = new File(filename);
        if (!file.exists()) {
            log.debug("外部config目录查找");
            file = new File("config/" + filename);
        }
        if (!file.exists()) {
            log.debug("进入classpath目录查找");
            resource = new ClassPathResource(filename);
            if (resource.exists()) return resource.getInputStream();
        }
        if (!file.exists()) {
            log.debug("进入classpath下config目录查找");
            resource = new ClassPathResource("config/" + filename);
            if (resource.exists()) return resource.getInputStream();
        }
        return new FileInputStream(file);
    }
}
