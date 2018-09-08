package com.pyg.seckill.service.impl;

import com.pyg.mapper.TbSeckillGoodsMapper;
import com.pyg.pojo.TbSeckillGoods;
import com.pyg.pojo.TbSeckillOrder;
import com.pyg.uitls.IdWorker;
import com.pyg.uitls.SysConstants;
import com.pyg.vo.OrderRecode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 1，从redis队列中获取用户排队信息
 *      排队信息：userid，seckillid
 *      使用set集合存储：用户排序信息userid
 * 2，判断用户排队信息是否存在
 * 3，如果用户存在，判断秒杀商品是否存在
 * 4，如果秒杀商品不存在，表示商品已售罄
 * 5，秒杀下单
 */
@Component
public class CreateOrder implements Runnable{

    //注入redis模板对象
    @Autowired
    private RedisTemplate redisTemplate;

    //注入秒杀商品mapper接口代理对象
    @Autowired
    private TbSeckillGoodsMapper seckillGoodsMapper;

    /**
     * 多线程下单
     */
    public void run() {

        //1，从redis队列中获取用户排队信息
        OrderRecode orderRecode = (OrderRecode) redisTemplate.boundListOps(SysConstants.SECKILL_USER_RECODE).rightPop();
        //2，判断用户排队信息是否存在
        if(orderRecode != null){

            //优化：从秒杀队列中获取商品id
            //从队列中获取数据：此时必须要进行排队
            Long seckillGoddsId = (Long) redisTemplate.boundListOps(SysConstants.SECKILL_GOODSID_LIST + orderRecode.getSeckillId()).rightPop();

            //2），判断商品是否存在，
            if (seckillGoddsId == null ) {
                //将用户从排队中移除，否则用户不能继续排队
                redisTemplate.boundSetOps(SysConstants.SECKILL_USER_QUEUE).remove(orderRecode.getUserId());
                throw new RuntimeException("已售罄");
            }

            //1），从redis服务器中获取入库的秒杀商品
            TbSeckillGoods seckillGoods = (TbSeckillGoods) redisTemplate.boundHashOps(TbSeckillGoods.class.getSimpleName()).get(orderRecode.getSeckillId());
            //3），如果秒杀商品存在，创建秒杀订单,此订单此时处于未支付状态
            if(seckillGoods != null) {
                //创建秒杀订单对象
                TbSeckillOrder seckillOrder = new TbSeckillOrder();
                //创建id worker
                IdWorker idWorker = new IdWorker();
                seckillOrder.setId(idWorker.nextId());
                //设置秒杀订单的值
                seckillOrder.setSellerId(seckillGoods.getSellerId());
                seckillOrder.setSeckillId(orderRecode.getSeckillId());
                //未支付状态
                seckillOrder.setStatus("0");
                seckillOrder.setUserId(orderRecode.getUserId());
                seckillOrder.setMoney(seckillGoods.getCostPrice());
                seckillOrder.setCreateTime(new Date());

                //4），把新增订单存储在redis服务器中
                //参数1：订单唯一标识，标识此数据是秒杀订单
                //参数2： userid，用来表示此订单属于哪个用户
                //参数3：秒杀订单数据
                redisTemplate.boundHashOps(TbSeckillOrder.class.getSimpleName()).put(orderRecode.getUserId(), seckillOrder);

                //5），下订单后，把秒杀商品库存减一
                seckillGoods.setStockCount(seckillGoods.getStockCount() - 1);

                //6），判断库存是否小于0,卖完需要同步数据库
                if (seckillGoods.getStockCount() < 1) {
                    //卖完需要同步数据库
                    seckillGoodsMapper.updateByPrimaryKeySelective(seckillGoods);
                    //清除缓存
                    redisTemplate.boundHashOps(TbSeckillGoods.class.getSimpleName()).delete(seckillGoods.getId());
                } else {
                    //7），否则把库存减少（但是此时没有减为0）的秒杀商品同步redis
                    redisTemplate.boundHashOps(TbSeckillGoods.class.getSimpleName()).put(orderRecode.getSeckillId(), seckillGoods);
                }

                //对抢购人数减一
                redisTemplate.boundValueOps(SysConstants.SECKILL_USER_COUNT + orderRecode.getSeckillId()).increment(-1);
            }
        }

    }
}
