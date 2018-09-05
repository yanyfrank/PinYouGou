package com.pyg.cart.service;

import com.pyg.vo.Cart;

import java.util.List;

public interface CartService {

    /**
     * 需求：查询redis购物车数据
     * 参数：用户名
     * 返回值：List<Cart>
     */
    public List<Cart> findRedisCartList(String username);

    /**
     * 需求：把新添加的购物车数据追加到原有购物车列表中，组合成新的购物车列表
     * @param cartList
     * @param itemId
     * @param num
     * @return
     */
    List<Cart> addItemsToCartList(List<Cart> cartList, Long itemId, Integer num);

    /**
     * 需求：添加redis购物车
     * @param cartList
     * @param username
     */
    void addRedisCart(List<Cart> cartList, String username);

    /**
     * 需求：合并购物车数据
     * @param redisCartList
     * @param cookieCartList
     * @return
     */
    List<Cart> mergeCart(List<Cart> redisCartList, List<Cart> cookieCartList);


}
