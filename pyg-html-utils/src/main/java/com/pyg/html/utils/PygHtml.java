package com.pyg.html.utils;

import com.pyg.mapper.TbGoodsDescMapper;
import com.pyg.mapper.TbGoodsMapper;
import com.pyg.mapper.TbItemCatMapper;
import com.pyg.mapper.TbItemMapper;
import com.pyg.pojo.*;
import com.pyg.uitls.FMUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PygHtml {

    //模板需要商品sku，商品描述，商品sku
    //输入商品对象sku
    @Autowired
    private TbItemMapper itemMapper;

    //注入商品spu对象
    @Autowired
    private TbGoodsMapper goodsMapper;

    //注入商品spu描述对象
    @Autowired
    private TbGoodsDescMapper goodsDescMapper;

    //注入商品分类mapper接口代理
    @Autowired
    private TbItemCatMapper itemCatMapper;


    /**
     *需求：生成静态页面
     */
    public void genHTML(){

        try {
            //创建example对象
            TbGoodsExample example = new TbGoodsExample();
            TbGoodsExample.Criteria criteria = example.createCriteria();
            //设置参数
            criteria.andAuditStatusEqualTo("1");
            criteria.andCategory1IdIsNotNull();
            criteria.andCategory2IdIsNotNull();
            criteria.andCategory3IdIsNotNull();

            //先查询spu 商品数据
            List<TbGoods> goodsList = goodsMapper.selectByExample(example);

            //循环spu商品，每一个spu对应静态页面
            for (TbGoods tbGoods : goodsList) {

                //创建商品描述example对象
                TbGoodsDescExample goodsDescExample = new TbGoodsDescExample();
                TbGoodsDescExample.Criteria goodsDescExampleCriteria = goodsDescExample.createCriteria();
                //设置参数
                goodsDescExampleCriteria.andItemImagesIsNotNull();
                goodsDescExampleCriteria.andGoodsIdEqualTo(tbGoods.getId());

                //查询此商品对应商品描述对象
                TbGoodsDesc tbGoodsDesc = goodsDescMapper.selectByExample(goodsDescExample).get(0);
                //查询商品sku列表
                TbItemExample example1 = new TbItemExample();
                TbItemExample.Criteria criteria1 = example1.createCriteria();
                //设置查询参数
                criteria1.andGoodsIdEqualTo(tbGoods.getId());
                //执行查询
                List<TbItem> itemList = itemMapper.selectByExample(example1);

                //查询第一级分类
                TbItemCat cat1 = itemCatMapper.selectByPrimaryKey(tbGoods.getCategory1Id());
                //查询第二级分类
                TbItemCat cat2 = itemCatMapper.selectByPrimaryKey(tbGoods.getCategory2Id());
                //查询第三级分类
                TbItemCat cat3 = itemCatMapper.selectByPrimaryKey(tbGoods.getCategory3Id());

                //创建map对象，封装模板页面需要的数据
                Map maps = new HashMap();
                maps.put("itemList",itemList);
                maps.put("goodsDesc",tbGoodsDesc);
                maps.put("goods",tbGoods);
                //面包屑导航
                maps.put("cat1",cat1.getName());
                maps.put("cat2",cat2.getName());
                maps.put("cat3",cat3.getName());


                //创建生成静态页面工具类，生成静态页面
                FMUtils fmUtils = new FMUtils();
                fmUtils.ouputFile("item.com.pyg.ftl",tbGoods.getId()+".html",maps);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args) {
        //加载配置文件
        ApplicationContext app = new ClassPathXmlApplicationContext("classpath*:spring/*.xml");
        //获取初始化生成静态页面对象
        PygHtml pygHtml = app.getBean(PygHtml.class);
        //调用生成静态页面的方法
        pygHtml.genHTML();
    }

}
