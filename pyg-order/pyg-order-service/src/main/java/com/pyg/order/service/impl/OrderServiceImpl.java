package com.pyg.order.service.impl;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.pyg.mapper.TbOrderItemMapper;
import com.pyg.pojo.TbOrderItem;
import com.pyg.uitls.IdWorker;
import com.pyg.vo.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pyg.mapper.TbOrderMapper;
import com.pyg.pojo.TbOrder;
import com.pyg.pojo.TbOrderExample;
import com.pyg.pojo.TbOrderExample.Criteria;
import com.pyg.order.service.OrderService;

import com.pyg.uitls.PageResult;


/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private TbOrderMapper orderMapper;

	//注入订单明细mapper接口代理对象
	@Autowired
	private TbOrderItemMapper orderItemMapper;
	
	/**
	 * 查询全部
	 */
	@Override
	public List<TbOrder> findAll() {
		return orderMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		Page<TbOrder> page=   (Page<TbOrder>) orderMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 增加
	 */
	@Override
	public void add(TbOrder order) {
		orderMapper.insert(order);		
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(TbOrder order){
		orderMapper.updateByPrimaryKey(order);
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public TbOrder findOne(Long id){
		return orderMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		for(Long id:ids){
			orderMapper.deleteByPrimaryKey(id);
		}		
	}
	
	
		@Override
	public PageResult findPage(TbOrder order, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbOrderExample example=new TbOrderExample();
		Criteria criteria = example.createCriteria();
		
		if(order!=null){			
						if(order.getPaymentType()!=null && order.getPaymentType().length()>0){
				criteria.andPaymentTypeLike("%"+order.getPaymentType()+"%");
			}
			if(order.getPostFee()!=null && order.getPostFee().length()>0){
				criteria.andPostFeeLike("%"+order.getPostFee()+"%");
			}
			if(order.getStatus()!=null && order.getStatus().length()>0){
				criteria.andStatusLike("%"+order.getStatus()+"%");
			}
			if(order.getShippingName()!=null && order.getShippingName().length()>0){
				criteria.andShippingNameLike("%"+order.getShippingName()+"%");
			}
			if(order.getShippingCode()!=null && order.getShippingCode().length()>0){
				criteria.andShippingCodeLike("%"+order.getShippingCode()+"%");
			}
			if(order.getUserId()!=null && order.getUserId().length()>0){
				criteria.andUserIdLike("%"+order.getUserId()+"%");
			}
			if(order.getBuyerMessage()!=null && order.getBuyerMessage().length()>0){
				criteria.andBuyerMessageLike("%"+order.getBuyerMessage()+"%");
			}
			if(order.getBuyerNick()!=null && order.getBuyerNick().length()>0){
				criteria.andBuyerNickLike("%"+order.getBuyerNick()+"%");
			}
			if(order.getBuyerRate()!=null && order.getBuyerRate().length()>0){
				criteria.andBuyerRateLike("%"+order.getBuyerRate()+"%");
			}
			if(order.getReceiverAreaName()!=null && order.getReceiverAreaName().length()>0){
				criteria.andReceiverAreaNameLike("%"+order.getReceiverAreaName()+"%");
			}
			if(order.getReceiverMobile()!=null && order.getReceiverMobile().length()>0){
				criteria.andReceiverMobileLike("%"+order.getReceiverMobile()+"%");
			}
			if(order.getReceiverZipCode()!=null && order.getReceiverZipCode().length()>0){
				criteria.andReceiverZipCodeLike("%"+order.getReceiverZipCode()+"%");
			}
			if(order.getReceiver()!=null && order.getReceiver().length()>0){
				criteria.andReceiverLike("%"+order.getReceiver()+"%");
			}
			if(order.getInvoiceType()!=null && order.getInvoiceType().length()>0){
				criteria.andInvoiceTypeLike("%"+order.getInvoiceType()+"%");
			}
			if(order.getSourceType()!=null && order.getSourceType().length()>0){
				criteria.andSourceTypeLike("%"+order.getSourceType()+"%");
			}
			if(order.getSellerId()!=null && order.getSellerId().length()>0){
				criteria.andSellerIdLike("%"+order.getSellerId()+"%");
			}
	
		}
		
		Page<TbOrder> page= (Page<TbOrder>)orderMapper.selectByExample(example);		
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 需求：提交订单
	 * 业务分析：
	 * 保存订单数据，同时保存订单明细数据
	 * 订单和订单明细一对多关系
	 * 1，一个商家一个订单
	 * 2，一个商家有多个订单明细（商品）
	 * 订单明细数据 来自购物车数据
	 * @param order
	 * @param userId
	 */
	@Override
	public void submitOrder(TbOrder order, String userId,List<Cart> cartList) {

		//循环购物车对象
		//每一个商家都会对应一个订单对象
		for (Cart cart : cartList) {

			IdWorker idWorker = new IdWorker();
			//生成订单id，包装订单id唯一性
			long orderId = idWorker.nextId();

			//定义变量，计算不同商家的商品总价格
			double totalPrice = 0;

			//获取订单明细
			List<TbOrderItem> orderItemList = cart.getOrderItemList();
			//循环订单明细
			for (TbOrderItem orderItem : orderItemList) {

				totalPrice += orderItem.getTotalFee().doubleValue();

				//生成订单明细id
				long orderItemId = idWorker.nextId();
				orderItem.setId(orderItemId);
				//设置订单id
				orderItem.setOrderId(orderId);

				//保存
				orderItemMapper.insertSelective(orderItem);

			}

			//创建订单对象
			TbOrder order1 = new TbOrder();
			//保存订单数据
			order1.setOrderId(orderId);

			//设置当前商家的总金额
			order1.setPayment(new BigDecimal(totalPrice));
			//设置支付方式
			order1.setPaymentType(order.getPaymentType());
			//设置收货人地址
			order1.setReceiverAreaName(order.getReceiverAreaName());
			//设置收货人
			order1.setReceiver(order.getReceiver());
			//设置收货人手机号码
			order1.setReceiverMobile(order.getReceiverMobile());

			//保存邮费
			order1.setPostFee("0");
			//状态
			order1.setStatus("1");
			//设置用户id
			order1.setUserId(userId);
			//设置订单来源
			order1.setSourceType("2");
			//设置商家id
			order1.setSellerId(cart.getSellerId());

			//设置订单创建时间
			Date date = new Date();
			order1.setCreateTime(date);
			order1.setUpdateTime(date);

			//保存订单
			orderMapper.insertSelective(order1);
		}


	}

}
