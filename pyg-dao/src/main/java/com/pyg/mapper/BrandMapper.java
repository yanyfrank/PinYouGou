package com.pyg.mapper;

import com.pyg.pojo.TbBrand;

import java.util.List;
import java.util.Map;

public interface BrandMapper {

    /**
     * 需求：查询所有品牌数据
     */
    List<TbBrand> findAll();

    /**
     * 需求：实现品牌数据添加
     */
    void save(TbBrand tbBrand);

    /**
     * 需求：根据id查询品牌数据
     */
    TbBrand findById(Long id);

    /**
     * 需求：更新品牌数据
     */
    void update(TbBrand tbBrand);

    /**
     * 需求：删除品牌
     */
    void delete(Long id);

    /**
     * 需求：条件查询品牌数据
     */
    //List<TbBrand> findCondition(TbBrand brand);

    /**
     * 定义查询请求，查询品牌下拉列表，进行多项选择
     */
    List<Map> findBrandSelect2List();
}
