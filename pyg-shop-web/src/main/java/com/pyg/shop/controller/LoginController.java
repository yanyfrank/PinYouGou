package com.pyg.shop.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {

    /**
     * 需求：获取用户登陆名，进行回显
     */
    @RequestMapping("showName")
    public Map showLoginName(){

        //获取用户登录名
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        //创建map封装登陆名
        Map loginMap = new HashMap();
        loginMap.put("loginName",name);
        return loginMap;
    }
}
