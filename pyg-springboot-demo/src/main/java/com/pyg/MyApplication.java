package com.pyg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyApplication {

    public static void main(String[] args) {
        //入口方法
        //1，加载内置tomcat服务器
        //2，引导项目启动环境
        SpringApplication.run(MyApplication.class,args);
    }
}
