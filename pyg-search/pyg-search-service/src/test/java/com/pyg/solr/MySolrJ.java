package com.pyg.solr;

import com.pyg.pojo.TbItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.HighlightEntry;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.data.solr.core.query.result.ScoredPage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/applicationContext-solr.xml")
public class MySolrJ {

    //注入solr模板对象
    @Autowired
    private SolrTemplate solrTemplate;

    /**
     * 需求：spring data solr 入门案例
     * 要求数据台南佳到索引库
     */

    @Test
    public void addIndex() {
        //创建商品对象
        TbItem item = new TbItem();
        item.setId(1000000000L);
        item.setTitle("pojo数据，通过注解域字段名称映射到索引库");
        item.setSellPoint("非常简单,确实非常简单");
        item.setBrand("spring data solr");

        solrTemplate.saveBean(item);

        solrTemplate.commit();
    }


    /**
     * 索引库删除
     */
    @Test
    public void deleteIndex() {
        //根据id删除
        //solrTemplate.deleteById("1000000000");
        //创建集合
        List<String> ids = new ArrayList<>();
        ids.add("1000000000");
        solrTemplate.deleteById(ids);

        solrTemplate.commit();
    }


    /**
     * 需求：根据id查询
     */
    @Test
    public void findId() {
        TbItem item = solrTemplate.getById(1000000000, TbItem.class);
        System.out.println(item);

    }

    /**
     * 需求：分页查询
     */
    @Test
    public void findByPage(){
        //创建query对象，封装所有查询条件
        Query query = new SimpleQuery("*:*");

        //设置查询条件,分页查询的其实位置，和每页显示的条数
        query.setOffset(0);
        query.setRows(10);

        //执行查询
        ScoredPage<TbItem> itemList = solrTemplate.queryForPage(query, TbItem.class);

        //获取查询总记录数
        long totalElements = itemList.getTotalElements();
        System.out.println("总记录数："+totalElements);
        //总记录集合数据
        List<TbItem> list = itemList.getContent();
        System.out.println(list);

    }

    /**
     * 需求：条件查询
     */
    @Test
    public void findByCondition(){
        //创建query对象，封装所有查询条件
        Query query = new SimpleQuery();

        //创建criteria对象,封装条件
        //is：包含  语法： item_title:数据
        Criteria criteria = new Criteria("item_title").is("数据");
        criteria.and("item_title").contains("pojo");
        query.addCriteria(criteria);

        //设置查询条件,分页查询的其实位置，和每页显示的条数
        query.setOffset(0);
        query.setRows(10);

        //执行查询
        ScoredPage<TbItem> itemList = solrTemplate.queryForPage(query, TbItem.class);

        //获取查询总记录数
        long totalElements = itemList.getTotalElements();
        System.out.println("总记录数："+totalElements);
        //总记录集合数据
        List<TbItem> list = itemList.getContent();
        System.out.println(list);

    }

    /**
     * 需求：高亮查询
     */
    @Test
    public void findHighLight(){
        //创建高亮query对象，封装所有查询条件
        SimpleHighlightQuery query = new SimpleHighlightQuery();

        //创建criteria对象,封装条件
        //is：包含  语法： item_title:数据
        Criteria criteria = new Criteria("item_title").is("数据");
        query.addCriteria(criteria);

        //设置高亮
        //创建高亮对象
        HighlightOptions options = new HighlightOptions();
        //设置高亮字段
        options.addField("item_title");
        //设置高亮前缀
        options.setSimplePrefix("<font color='red'>");
        //设置高亮后缀
        options.setSimplePostfix("</font>");

        //把高亮对象添加到query查询对象
        query.setHighlightOptions(options);

        //设置查询条件,分页查询的其实位置，和每页显示的条数
        query.setOffset(0);
        query.setRows(10);

        //执行查询
        HighlightPage<TbItem> itemList = solrTemplate.queryForHighlightPage(query, TbItem.class);

        //获取查询总记录数
        long totalElements = itemList.getTotalElements();
        System.out.println("总记录数："+totalElements);
        //总记录集合数据
        List<TbItem> list = itemList.getContent();

        //循环
        for(TbItem tbItem : list){
            //获取高亮
            List<HighlightEntry.Highlight> highlights = itemList.getHighlights(tbItem);
            String title = highlights.get(0).getSnipplets().get(0);

            System.out.println(title);
        }
        System.out.println(list);

    }
}
