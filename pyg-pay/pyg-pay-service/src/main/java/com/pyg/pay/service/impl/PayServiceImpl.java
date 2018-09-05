package com.pyg.pay.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.wxpay.sdk.WXPayUtil;
import com.pyg.mapper.TbOrderMapper;
import com.pyg.pay.service.PayService;
import com.pyg.pojo.TbOrder;
import com.pyg.pojo.TbOrderExample;
import com.pyg.uitls.HttpClient;
import com.pyg.uitls.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.naming.Context;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PayServiceImpl implements PayService {

    //注入商户appid
    @Value("${appid}")
    private String appid;

    //注入商户号
    @Value("${partner}")
    private String partner;

    //注入商户密钥
    @Value("${partnerkey}")
    private String partnerkey;

    //注入下单那地址
    @Value("${payUrl}")
    private String payUrl;

    //注入订单mapper接口代理对象
    @Autowired
    private TbOrderMapper orderMapper;

    /**
     * 需求：生成二维码
     * @param userId
     * @return
     */
    @Override
    public Map createQrCode(String userId) {

        try {
            //查询支付金额
            //创建example对象
            TbOrderExample example = new TbOrderExample();
            //创建criteria对象
            TbOrderExample.Criteria criteria = example.createCriteria();
            //设置查询条件
            criteria.andUserIdEqualTo(userId);
            //执行查询
            List<TbOrder> tbOrders = orderMapper.selectByExample(example);

            //定风翼记录总金额变量
            Double totalFee = 0d;

            //循环多个订单，计算总金额
            for (TbOrder tbOrder : tbOrders) {
                totalFee+=tbOrder.getPayment().doubleValue();
            }


            //创建一个map对象，封装支付下单参数
            Map<String,String> maps = new HashMap<>();
            //公众号id
            maps.put("appid",appid);
            //商户号
            maps.put("mch_id",partner);
            //随机字符串
            maps.put("nonce_str", WXPayUtil.generateNonceStr());
            //商品描述
            maps.put("body","品优购");

            //创建idworker对象，生成支付订单号
            IdWorker idWorker = new IdWorker();
            long payId = idWorker.nextId();

            //商户订单号
            maps.put("out_trade_no",payId+"");

            //设置支付金额
            maps.put("total_fee","1");
            //终端ip
            maps.put("spbill_create_ip","127.0.0.1");
            //通知地址
            maps.put("notify_url","http://test.itcast.cn");
            //交易类型
            maps.put("trade_type","NATIVE");

            //使用微信支付工具类对象，生成一个具有签名的xml格式参数
            String xmlParam = WXPayUtil.generateSignedXml(maps, partnerkey);

            //创建httpclient对象，向微信支付平台发送请求，获取支付地址
            HttpClient httpClient = new HttpClient(payUrl);
            //设置请求方式
            httpClient.setHttps(true);
            //设置请求参数
            httpClient.setXmlParam(xmlParam);
            //设置请求方式
            httpClient.post();

            //获取回调结果
            String content = httpClient.getContent();
            //把xml格式转换成对象
            //返回支付地址
            Map<String, String> stringMap = WXPayUtil.xmlToMap(content);

            //金额
            stringMap.put("total_fee",totalFee+"");
            //订单号
            stringMap.put("out_trade_no",payId+"");

            return stringMap;
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }

    /**
     * 需求：实时监控二维码的状态，及时发现二维码支付成功
     * 参数：订单号
     * 向微信支付平台发送请求：查询订单状态
     * 1，封装向微信传递参数
     * 2，把map参数转换为xml格式（此格式必须带签名）
     * 3，使用ttpclient发送请求（向微信支付平台）
     * 4，获取微信支付返回结果
     * 5，把结果返回给表现层
     * 6，判断是否支付成功
     */
    @Override
    public Map queryStatus(String out_trade_no) {

        try {
            //创建map对象，封装查询微信支付状态参数
            Map<String,String> maps = new HashMap<>();
            //公众号id
            maps.put("appid",appid);
            //商户号
            maps.put("mch_id",partner);
            //随机字符串
            maps.put("nonce_str", WXPayUtil.generateNonceStr());
            //商户订单号
            maps.put("out_trade_no",out_trade_no);

            //具有签名的xml格式
            String xmlParam = WXPayUtil.generateSignedXml(maps, partnerkey);

            //使用httpclient向微信支付平台发送查询订单请求
            HttpClient httpClient = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            //设置请求方式
            httpClient.setHttps(true);
            //设置参数
            httpClient.setXmlParam(xmlParam);
            //请求方式
            httpClient.post();

            //获取查询结果
            String content = httpClient.getContent();

            //把返回结果转换成map对象
            Map<String, String> stringMap = WXPayUtil.xmlToMap(content);

            //返回支付状态
            return stringMap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
