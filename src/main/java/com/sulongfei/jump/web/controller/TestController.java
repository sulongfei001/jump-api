package com.sulongfei.jump.web.controller;

import com.sulongfei.jump.service.RoomSimpleService;
import com.sulongfei.jump.service.TaskService;
import com.sulongfei.jump.web.socket.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/5/29 16:13
 * @Version 1.0
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private TaskService taskService;
    @Autowired
    private RoomSimpleService roomService;

    @GetMapping("/t1")
    public void test() throws IOException {
//        taskService.settle();
        Map<String, Object> map = new HashMap<>();
        map.put("type", 0);
        map.put("content", "恭喜" + "lallal" + "，刚刚获得了" + "Iphone XXXX");
        WebSocketServer.sendInfo(null, map);
        Map<String, Object> newMap = new HashMap<>();
        newMap.put("type", 1);
        WebSocketServer.sendInfo(38L, newMap);
    }
}
