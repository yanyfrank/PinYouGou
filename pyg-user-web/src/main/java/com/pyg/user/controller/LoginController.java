package com.pyg.user.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {

    /**
     * 需求：获取用户的登陆信息
     */
    @RequestMapping("loadLoginName")
    public Map loadLoginName(){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        //创建map对象，获取用户名
        Map maps = new HashMap();
        maps.put("loginName",name);
        return maps;
    }
}
