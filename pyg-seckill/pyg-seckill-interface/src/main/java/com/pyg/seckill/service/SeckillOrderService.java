package com.pyg.seckill.service;
import java.util.List;
import com.pyg.pojo.TbSeckillOrder;

import com.pyg.uitls.PageResult;

/**
 * 服务层接口
 * @author Administrator
 *
 */
public interface SeckillOrderService {

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<TbSeckillOrder> findAll();
	
	
	/**
	 * 返回分页列表
	 * @return
	 */
	public PageResult findPage(int pageNum, int pageSize);
	
	
	/**
	 * 增加
	*/
	public void add(TbSeckillOrder seckillOrder);
	
	
	/**
	 * 修改
	 */
	public void update(TbSeckillOrder seckillOrder);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public TbSeckillOrder findOne(Long id);
	
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public void delete(Long[] ids);

	/**
	 * 分页
	 * @param pageNum 当前页 码
	 * @param pageSize 每页记录数
	 * @return
	 */
	public PageResult findPage(TbSeckillOrder seckillOrder, int pageNum, int pageSize);

	/**
	 * 需求：提交订单
	 * 参数Long id string userid
	 */
    void submitOrder(Long id, String userId);

	/**
	 * 查询用户秒杀订单
	 */
	TbSeckillOrder getByUserId(String userId);

	/**
	 * 修改支付数据，并将数据入库
	 * @param userId
	 * @param out_trade_no
	 *
	 */
	void updateStatus(String userId, String out_trade_no, String status);

	/**
	 * 移除用户超时秒杀订单
	 */
	void removeRedisOrder(String userId);
}
