package com.pyg.manager.service;

import com.pyg.uitls.PageResult;
import com.pyg.vo.Specification;

import java.util.List;
import java.util.Map;

public interface SpecificationService {


    /**
     * 需求：规格分页查询展示
     * 参数：Integer page，Integer rows
     * 返回值：分页包装类对象
     */
    PageResult findByPage(Integer page, Integer rows);

    /**
     * 需求：实现规格及选项的数据添加
     */
    void save(Specification specification);

    /**
     * 需求：根据id查询规格及规格选项数据
     */
    Specification findById(Long id);

    /**
     * 需求：更新规格数据
     */
    void update(Specification specification);

    /**
     * 需求：删除规格
     */
    void delete(Long[] ids);

    /**
     * 定义方法：实现规格下拉列表，实现规格多项选择
     */
    List<Map> findSpecList();
}
