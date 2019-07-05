package com.sulongfei.jump.service;

import com.sulongfei.jump.dto.BaseDTO;
import com.sulongfei.jump.response.Response;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/7/5 14:28
 * @Version 1.0
 */
public interface UploadService {
    @Transactional(readOnly = false)
    Response uploadScale(BaseDTO dto, MultipartFile file) throws Exception;

    @Transactional(readOnly = false)
    Response uploadFile(BaseDTO dto, MultipartFile file) throws Exception;
}
