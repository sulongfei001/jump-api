package com.sulongfei.jump.web.controller;

import com.google.common.collect.Maps;
import com.sulongfei.jump.dto.BaseDTO;
import com.sulongfei.jump.response.Response;
import com.sulongfei.jump.utils.Base64DecodeMultipartFile;
import com.sulongfei.jump.utils.QCloudConfiguration;
import com.sulongfei.jump.utils.QCloudUpload;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
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

    @ApiOperation(value = "上传图片")
    @PostMapping("/upload")
    public Response<Map<String, String>> upload(
            @ApiParam(value = "基础请求数据", hidden = true) BaseDTO dto,
            @ApiParam(value = "上传的图片", required = true) MultipartFile file) throws IOException {
        verifyBaseDTO(dto);
        String originalFilename = file.getOriginalFilename();
        InputStream inputStream = file.getInputStream();
        String key = QCloudUpload.uploadToStream(inputStream, originalFilename);
        String url = QCloudConfiguration.getUrl(key);
        Map<String, String> result = Maps.newConcurrentMap();
        result.put("name", originalFilename);
        result.put("url", url);
        return new Response<>(result);
    }

    @ApiOperation(value = "上传图片")
    @PostMapping("/uploadBase64")
    public Response<Map<String, String>> uploadBase64(
            @ApiParam(value = "基础请求数据", hidden = true) BaseDTO dto,
            @ApiParam(value = "图片字符串", required = true) @RequestBody String base64) throws IOException {
        verifyBaseDTO(dto);
        MultipartFile file = Base64DecodeMultipartFile.base64Convert(base64);
        String originalFilename = file.getOriginalFilename();
        InputStream inputStream = file.getInputStream();
        String key = QCloudUpload.uploadToStream(inputStream, originalFilename);
        String url = QCloudConfiguration.getUrl(key);
        Map<String, String> result = Maps.newConcurrentMap();
        result.put("name", originalFilename);
        result.put("url", url);
        return new Response<>(result);
    }

}
