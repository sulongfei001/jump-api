package com.sulongfei.jump.web.controller;

import com.sulongfei.jump.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/t1")
    public void test() {
        taskService.resetRank();
    }
}
