package com.sulongfei.jump.web.controller;

import com.sulongfei.jump.dto.UserDTO;
import com.sulongfei.jump.response.Response;
import com.sulongfei.jump.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 〈〉
 *
 * @Author sulongfei
 * @Date 2019/5/30 11:43
 * @Version 1.0
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/info")
    public Response getUserInfo() {
        return userService.getUserInfo();
    }

    @PutMapping("/info/update")
    public Response updateUser(@RequestBody UserDTO dto) {
        return userService.updateUser(dto);
    }
}
