package com.sulongfei.jump.service.impl;

import com.alibaba.fastjson.JSON;
import com.sulongfei.jump.dto.UserLoginDTO;
import com.sulongfei.jump.rest.request.RegisterRequest;
import com.sulongfei.jump.rest.request.SendPrdRequest;
import com.sulongfei.jump.rest.response.RegisterResponse;
import com.sulongfei.jump.rest.response.RestResponse;
import com.sulongfei.jump.rest.response.SendPrdResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/5/30 15:38
 * @Version 1.0
 */
@Service
public class RestService {
    @Autowired
    private RestTemplate restTemplate;

    private static final String realmName = "http://dt.9hou.me/crmmarketdev";

    private static HttpHeaders headers = new HttpHeaders();

    static {
        MimeType mimeType = MimeTypeUtils.parseMimeType("application/json");
        MediaType mediaType = new MediaType(mimeType.getType(), mimeType.getSubtype(), Charset.forName("UTF-8"));
        headers.setContentType(mediaType);
    }


    public ResponseEntity<RestResponse<RegisterResponse>> register(UserLoginDTO dto) {
        String url = realmName + "/gamePlat/getMemberId";
        RegisterRequest request = new RegisterRequest(dto.getPhoneNumber(), dto.getRemoteClubId(), dto.getSaleType(), dto.getSaleId(), dto.getRegisterTime());

        ResponseEntity<RestResponse<RegisterResponse>> result = restTemplate.exchange(url,
                HttpMethod.POST,
                new HttpEntity<>(JSON.toJSONString(request), headers),
                new ParameterizedTypeReference<RestResponse<RegisterResponse>>() {
                });
        return result;
    }

    public ResponseEntity<SendPrdResponse> sendPrd(SendPrdRequest request) {
        String url = "";
        ResponseEntity<SendPrdResponse> result = restTemplate.postForEntity(url, request, SendPrdResponse.class);
        return result;
    }
}
