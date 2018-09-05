package com.pyg.pay.service;

import java.util.Map;

public interface PayService {

    /**
     * 需求：生成二维码
     * @param userId
     * @return
     */
    Map createQrCode(String userId);


    /**
     * 需求：实时监控二维码的状态，及时发现二维码支付成功
     * 参数：订单号
     */
    Map queryStatus(String out_trade_no);
}
