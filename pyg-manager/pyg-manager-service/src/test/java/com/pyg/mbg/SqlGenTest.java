package com.pyg.mbg;

import com.pyg.mapper.TbItemMapper;
import com.pyg.mapper.TbProvincesMapper;
import com.pyg.pojo.TbBrand;
import com.pyg.pojo.TbProvinces;
import com.pyg.pojo.TbProvincesExample;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/applicationContext-dao.xml")
public class SqlGenTest {

    //注入mapper接口代理对象
    @Autowired
    private TbProvincesMapper provincesMapper;

    /**
     * 需求：添加一个商品数据
     */
    @Test
    public void insertItem(){
        TbProvinces provinces = new TbProvinces();
        provinces.setProvinceid("666666");
        provinces.setProvince("扬州");

        provincesMapper.insert(provinces);
    }

    @Test
    public void findAll(){
        List<TbProvinces> list = provincesMapper.selectByExample(null);
        System.out.println(list);
    }

    @Test
    public void findByCondition(){
        TbProvinces provinces = provincesMapper.selectByPrimaryKey(new Integer(24));
        System.out.println(provinces.getProvinceid()+provinces.getProvince());
    }

    @Test
    public void findCondition(){

        //创建example
        TbProvincesExample example = new TbProvincesExample();
        //创建criteria对象条件查询
        TbProvincesExample.Criteria criteria = example.createCriteria();
        //设置条件查询
        //包含“湖”的省份
        criteria.andProvinceLike("%湖%");

        //执行查询
        List<TbProvinces> list = provincesMapper.selectByExample(example);
        System.out.println(list);
    }

    @Test
    public void find(){

        //创建example
        TbProvincesExample example = new TbProvincesExample();

        //设置条件查询
        //包含“湖”的省份
        example.or().andProvinceidLike("%5%");
        example.or().andProvinceLike("%湖%");

        //执行查询
        List<TbProvinces> list = provincesMapper.selectByExample(example);
        System.out.println(list);
    }



}
