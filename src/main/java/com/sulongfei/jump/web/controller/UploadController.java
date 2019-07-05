package com.sulongfei.jump.web.controller;

import com.sulongfei.jump.dto.BaseDTO;
import com.sulongfei.jump.response.Response;
import com.sulongfei.jump.service.UploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/5/26 11:50
 * @Version 1.0
 */
@Api(tags = "文件操作")
@RestController
@RequestMapping("/{remoteClubId}/{saleId}/{saleType}/file")
public class UploadController extends BaseController {

    @Autowired
    private UploadService uploadService;

    @ApiOperation(value = "上传图片")
    @PostMapping("/upload")
    public Response<Map<String, String>> upload(
            @ApiParam(value = "基础请求数据", hidden = true) BaseDTO dto,
            @ApiParam(value = "上传的图片", required = true) MultipartFile file) throws Exception {
        verifyBaseDTO(dto);
        return uploadService.uploadScale(dto, file);
    }

}
