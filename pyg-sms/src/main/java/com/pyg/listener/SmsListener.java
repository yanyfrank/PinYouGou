package com.pyg.listener;

import com.aliyuncs.exceptions.ClientException;
import com.pyg.utils.SmsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SmsListener {

    //注入对象
    @Autowired
    private SmsUtils smsUtils;

    @JmsListener(destination = "sms")
    public void sendSms(Map map){
        try {
            //从map中获取消息
            //签名
            String sign_name = (String) map.get("sign_name");
            //模板
            String template_code = (String) map.get("template_code");
            //验证码
            String code = (String) map.get("code");
            //手机号码
            String phone = (String) map.get("phone");

            //发送短信
            smsUtils.sendSms(sign_name,template_code,code,phone);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
