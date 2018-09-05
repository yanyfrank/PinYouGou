package com.pyg.pay.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pyg.pay.service.PayService;
import com.pyg.uitls.PygResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.AbstractMap;
import java.util.Map;

@RestController
@RequestMapping("/pay")
public class PayController {

    //注入远程支付对象
    @Reference(timeout = 10000000)
    private PayService payService;


    /**
     * 生成二维码
     * @return
     */
    @RequestMapping("createQrCode")
    public Map createQrCode(HttpServletRequest request){
        //获取用户名
        String userId = request.getRemoteUser();
        //调用服务层方法，向微信支付平台下单
        Map map = payService.createQrCode(userId);

        return map;
    }

    /**
     * 需求：实时监控二维码的状态，及时发现二维码支付成功
     * 参数：订单号
     */
    @RequestMapping("queryStatus/{out_trade_no}")
    public PygResult queryStatus(@PathVariable String out_trade_no){
        try {

            PygResult pygResult =null;

            int i =0;
            while(true){
                //调用远程服务，查询支付订单状态
                Map map = payService.queryStatus(out_trade_no);

                if(map==null){
                    pygResult = new PygResult(false,"支付出错");
                    break;
                }

                if(map.get("trade_state").equals("SUCCESS")){
                    pygResult = new PygResult(true,"支付成功");
                    break;
                }

                //间隔3秒
                Thread.sleep(3000);

                i++;

                if(i>=100){
                    pygResult = new PygResult(true,"二维码超时");
                    break;
                }

            }

            return pygResult;
        } catch (Exception e) {
            e.printStackTrace();
            return new PygResult(false,"支付异常");
        }
    }
}


