package com.sulongfei.jump.service;

import com.sulongfei.jump.dto.UserLoginDTO;
import com.sulongfei.jump.rest.request.RegisterRequest;
import com.sulongfei.jump.rest.request.SendPrdRequest;
import com.sulongfei.jump.rest.response.RegisterResponse;
import com.sulongfei.jump.rest.response.SendPrdResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

    public ResponseEntity<RegisterResponse> register(UserLoginDTO dto) {
        String url = "";
        RegisterRequest request = new RegisterRequest(dto.getPhoneNumber(), dto.getRemoteClubId(), dto.getSaleType(), dto.getSaleId(), dto.getRegisterTime());
        ResponseEntity<RegisterResponse> result = restTemplate.postForEntity(url, request, RegisterResponse.class);
        RegisterResponse response = restTemplate.postForObject(url, request, RegisterResponse.class);
        return result;
    }

    public ResponseEntity<SendPrdResponse> sendPrd(SendPrdRequest request) {
        String url = "";
        ResponseEntity<SendPrdResponse> result = restTemplate.postForEntity(url, request, SendPrdResponse.class);
        return result;
    }
}
