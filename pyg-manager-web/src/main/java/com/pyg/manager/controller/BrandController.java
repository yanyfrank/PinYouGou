package com.pyg.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pyg.manager.service.BrandService;
import com.pyg.pojo.TbBrand;
import com.pyg.uitls.PageResult;
import com.pyg.uitls.PygResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/brand")
public class BrandController {

    @Reference(timeout = 1000000)
    private BrandService brandService;

    /**
     * 需求，查询所有品牌
     */
    @RequestMapping("findAll")
    public List<TbBrand> findAll(){

        return brandService.findAll();
    }

    /**
     * 需求：品牌分页查询展示
     * 参数：Integer page，Integer rows
     * 返回值：分页包装类对象
     */
    @RequestMapping("findByPage/{page}/{rows}")
    public PageResult findByPage(@PathVariable Integer page, @PathVariable Integer rows){

        return brandService.findByPage(page,rows);
    }

    /**
     * 需求：实现品牌数据添加
     * 参数：tbBrand
     * 返回值：成功，或者失败
     * 前台页面时angularJS负责传递参数，由于angularJS传递的参数都是json格式
     * 因此在后台接受参数的时候，必须使用注解@RequestBody
     * @RequestBody自动json格式数据转换成pojo对象
     */
    @RequestMapping("add")
    public PygResult add(@RequestBody TbBrand tbBrand){
        try {
            //调用远程service服务对象方法
            brandService.save(tbBrand);
            return new PygResult(true,"保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new PygResult(false,"保存失败");
        }
    }

    /**
     * 需求：根据id查询品牌数据
     */
    @RequestMapping("findOne/{id}")
    public TbBrand findById(@PathVariable Long id){
        return brandService.findById(id);
    }

    /**
     * 需求：更新品牌数据
     */
    @RequestMapping("update")
    public PygResult update(@RequestBody TbBrand tbBrand){
        try {
            //调用远程service服务方法
            brandService.update(tbBrand);
            return new PygResult(true,"修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new PygResult(false,"修改失败");
        }
    }

    /**
     * 需求：删除品牌数据
     */
    @RequestMapping("delete/{ids}")
    public PygResult delete(@PathVariable Long[] ids){
        try {
            //调用远程service服务方法
            brandService.delete(ids);
            return new PygResult(true,"删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new PygResult(false,"删除失败");
        }
    }

    /**
     * 定义查询请求，查询品牌下拉列表，进行多项选择
     */
    @RequestMapping("findBrandSelect2List")
    public List<Map> findBrandSelect2List(){
        return brandService.findBrandSelect2List();
    }
}
