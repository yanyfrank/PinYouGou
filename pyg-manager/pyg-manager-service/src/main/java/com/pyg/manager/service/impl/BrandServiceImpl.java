package com.pyg.manager.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pyg.manager.service.BrandService;
import com.pyg.mapper.BrandMapper;
import com.pyg.pojo.TbBrand;
import com.pyg.uitls.PageResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Service
public  class BrandServiceImpl implements BrandService {

    //注入mapper接口代理对象
    @Autowired
    private BrandMapper brandMapper;

    @Override
    public List<TbBrand> findAll() {
        return brandMapper.findAll();
    }


    @Override
    public PageResult findByPage(Integer page, Integer rows) {
        //设置分页查询
        PageHelper.startPage(page,rows);
        //分页查询
        Page<TbBrand> pageInfo = (Page<TbBrand>) brandMapper.findAll();
        //返回
        return new PageResult(pageInfo.getTotal(),pageInfo.getResult());
    }

    @Override
    public void save(TbBrand tbBrand) {
        brandMapper.save(tbBrand);
    }

    @Override
    public TbBrand findById(Long id) {
        return brandMapper.findById(id);
    }

    @Override
    public void update(TbBrand tbBrand) {
        brandMapper.update(tbBrand);
    }

    @Override
    public void delete(Long[] ids) {
        //循环删除
        for(Long id : ids){
            brandMapper.delete(id);
        }
    }

    /**
     * 定义查询请求，查询品牌下拉列表，进行多项选择
     */
    @Override
    public List<Map> findBrandSelect2List() {
        //调用接口的方法
        List<Map> brandSelect2List = brandMapper.findBrandSelect2List();
        return brandSelect2List;
    }


}
