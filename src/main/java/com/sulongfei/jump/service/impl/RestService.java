package com.sulongfei.jump.service.impl;

import com.alibaba.fastjson.JSON;
import com.sulongfei.jump.dto.UserLoginDTO;
import com.sulongfei.jump.rest.request.*;
import com.sulongfei.jump.rest.response.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.List;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/5/30 15:38
 * @Version 1.0
 */
@Slf4j
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

    public ResponseEntity<RestResponse<BaseResponse>> sendPrd(SendPrdRequest request) {
        String url = realmName + "/gamePlat/sendPrd";
        ResponseEntity<RestResponse<BaseResponse>> result = restTemplate.exchange(url,
                HttpMethod.POST,
                new HttpEntity<>(JSON.toJSONString(request), headers),
                new ParameterizedTypeReference<RestResponse<BaseResponse>>() {
                });
        log.info("门店兑换结果：errorCode={},errMsg={}" + result.getBody().getErrorCode(), result.getBody().getErrMsg());
        return result;
    }

    public ResponseEntity<RestResponse<List<PrdResponse>>> getPrdList(PrdRequest request) {
        String url = realmName + "/gamePlat/getPrdList";
        ResponseEntity<RestResponse<List<PrdResponse>>> result = restTemplate.exchange(url,
                HttpMethod.POST,
                new HttpEntity<>(JSON.toJSONString(request), headers),
                new ParameterizedTypeReference<RestResponse<List<PrdResponse>>>() {
                });
        return result;
    }

    public ResponseEntity<RestResponse<BaseResponse>> createSendOrder(SendGoodsRequest request) {
        String url = realmName + "/gamePlat/createSendOrder";
        ResponseEntity<RestResponse<BaseResponse>> result = restTemplate.exchange(url,
                HttpMethod.POST,
                new HttpEntity<>(JSON.toJSONString(request), headers),
                new ParameterizedTypeReference<RestResponse<BaseResponse>>() {
                });
        log.info("卡券寄送结果：errorCode={},errMsg={}" + result.getBody().getErrorCode(), result.getBody().getErrMsg());
        return result;
    }

    public ResponseEntity<RestResponse<List<MarketOrderResponse>>> getMarketOrderList(MarketOrderRequest request) {
        String url = realmName + "/marketSendOrder/getMarketSendOrderList";
        ResponseEntity<RestResponse<List<MarketOrderResponse>>> result = restTemplate.exchange(url,
                HttpMethod.POST,
                new HttpEntity<>(JSON.toJSONString(request), headers),
                new ParameterizedTypeReference<RestResponse<List<MarketOrderResponse>>>() {
                });
        return result;
    }

}
