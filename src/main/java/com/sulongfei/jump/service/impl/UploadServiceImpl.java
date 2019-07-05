package com.sulongfei.jump.service.impl;

import cn.hutool.core.img.ImgUtil;
import com.google.common.collect.Maps;
import com.sulongfei.jump.dto.BaseDTO;
import com.sulongfei.jump.mapper.SecurityUserMapper;
import com.sulongfei.jump.model.SecurityUser;
import com.sulongfei.jump.response.Response;
import com.sulongfei.jump.service.UploadService;
import com.sulongfei.jump.utils.QCloudConfiguration;
import com.sulongfei.jump.utils.QCloudUpload;
import com.sulongfei.jump.web.interceptor.UserInterceptor;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.File;
import java.io.InputStream;
import java.util.Map;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/7/5 14:29
 * @Version 1.0
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class UploadServiceImpl implements UploadService {
    @Autowired
    private SecurityUserMapper userMapper;

    @Override
    @Transactional(readOnly = false)
    public Response uploadScale(BaseDTO dto, MultipartFile file) throws Exception {
        SecurityUser user = UserInterceptor.getLocalUser();
        String originalFilename = file.getOriginalFilename();
        File f1 = File.createTempFile("tmp1", ".jpg");
        File f2 = File.createTempFile("tmp2", ".jpg");
        file.transferTo(f1);
        Thumbnails.of(f1).size(140,140).keepAspectRatio(false).toFile(f2);
        String key = QCloudUpload.uploadToFile(f2, originalFilename);
        String url = QCloudConfiguration.getUrl(key);
        user.setAvatar(url);
        userMapper.updateByPrimaryKey(user);
        Map<String, String> result = Maps.newConcurrentMap();
        result.put("name", originalFilename);
        result.put("url", url);
        return new Response<>(result);
    }

    @Override
    @Transactional(readOnly = false)
    public Response uploadFile(BaseDTO dto, MultipartFile file) throws Exception {
        SecurityUser user = UserInterceptor.getLocalUser();
        String originalFilename = file.getOriginalFilename();
        InputStream is = file.getInputStream();
        String key = QCloudUpload.uploadToStream(is, originalFilename);
        String url = QCloudConfiguration.getUrl(key);
        user.setAvatar(url);
        userMapper.updateByPrimaryKey(user);
        Map<String, String> result = Maps.newConcurrentMap();
        result.put("name", originalFilename);
        result.put("url", url);
        return new Response<>(result);
    }
}
