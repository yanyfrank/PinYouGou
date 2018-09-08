package com.pyg.pay.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pyg.pay.service.PayService;
import com.pyg.pojo.TbSeckillOrder;
import com.pyg.seckill.service.SeckillOrderService;
import com.pyg.uitls.IdWorker;
import com.pyg.uitls.PygResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/pay")
public class PayController {

    //注入远程支付对象
    @Reference(timeout = 10000000)
    private PayService payService;

    @Reference(timeout = 10000000)
    private SeckillOrderService seckillOrderService;


    /**
     * 生成二维码
     * @return
     */
    @RequestMapping("createQrCode")
    public Map createQrCode(HttpServletRequest request){
       /* //创建idworker对象，生成支付订单号
        IdWorker idWorker = new IdWorker();
        long payId = idWorker.nextId();
        //调用服务层方法，向微信支付平台下单
        Map map = payService.createQrCode(payId+"","1");

        return map;*/
        //获取用户id
        String userId = request.getRemoteUser();
        //查询订单数据
        TbSeckillOrder seckillOrder = seckillOrderService.getByUserId(userId);
        //调用远程支付服务对象生成二维码
        Map qrCode = payService.createQrCode(seckillOrder.getId() + "", (long)(seckillOrder.getMoney().doubleValue() * 100) + "");

        if(qrCode == null){
            return new HashMap();
        }

        return qrCode;
    }

    /**
     * 需求：实时监控二维码的状态，及时发现二维码支付成功
     * 参数：订单号
     */
    @RequestMapping("queryStatus/{out_trade_no}")
    public PygResult queryStatus(@PathVariable String out_trade_no,HttpServletRequest request){
        try {
            //获取用户id
            String userId = request.getRemoteUser();
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
                    //修改订单状态
                    //参数1：用户名
                    //参数2：商户订单号【秒杀订单号】
                    //参数3：1 表示支付成功
                    seckillOrderService.updateStatus(userId,out_trade_no,"1");
                    pygResult = new PygResult(true,"支付成功");
                    break;
                }

                //间隔3秒
                Thread.sleep(3000);

                i++;

                if(i>=100){
                    //判断状态
                    if(map.get("trade_state").equals("SUCCESS")){
                        //修改订单状态
                        //参数1：用户名
                        //参数2：商户订单号【秒杀订单号】
                        //参数3：1 表示支付成功
                        seckillOrderService.updateStatus(userId,out_trade_no,"1");
                        pygResult = new PygResult(true,"支付成功");
                        break;
                    }

                    //恢复库存，删除订单
                    seckillOrderService.removeRedisOrder(userId);
                    //关闭微信订单
                    payService.closePay(out_trade_no);
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


