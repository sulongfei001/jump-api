package com.sulongfei.jump.config;

import org.codehaus.jackson.map.ObjectMapper;

import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import java.io.IOException;
import java.util.Map;

/**
 * @Description
 * @Author sulongfei
 * @Date 2018/12/27 17:47
 * @Version 1.0
 */
public class WebSocketEncoder implements Encoder.Text<Map> {


    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }


    @Override
    public String encode(Map map) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(map);
            return json;
        } catch (IOException e) {
            e.printStackTrace();
            return "false";
        }
    }
}
