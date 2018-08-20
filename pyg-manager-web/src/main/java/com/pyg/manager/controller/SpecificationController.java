package com.pyg.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pyg.manager.service.SpecificationService;
import com.pyg.pojo.TbSpecification;
import com.pyg.uitls.PageResult;
import com.pyg.uitls.PygResult;
import com.pyg.vo.Specification;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/specification")
public class SpecificationController {

    @Reference(timeout = 1000000)
    private SpecificationService specificationService;

    /**
     * 需求：规格分页查询展示
     * 参数：Integer page，Integer rows
     * 返回值：分页包装类对象
     */
    @RequestMapping("findByPage/{page}/{rows}")
    public PageResult findByPage(@PathVariable Integer page, @PathVariable Integer rows){

        return specificationService.findByPage(page,rows);
    }

    /**
     * 需求：实现规格数据添加
     * 参数：tbBrand
     * 返回值：成功，或者失败
     * 前台页面时angularJS负责传递参数，由于angularJS传递的参数都是json格式
     * 因此在后台接受参数的时候，必须使用注解@RequestBody
     * @RequestBody自动json格式数据转换成pojo对象
     */
    @RequestMapping("add")
    public PygResult add(@RequestBody Specification specification){
        try {
            //调用远程service服务对象方法
            specificationService.save(specification);
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
    public Specification findById(@PathVariable Long id){
        return specificationService.findById(id);
    }

    /**
     * 需求：更新品牌数据
     */
    @RequestMapping("update")
    public PygResult update(@RequestBody Specification specification){
        try {
            //调用远程service服务方法
            specificationService.update(specification);
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
            specificationService.delete(ids);
            return new PygResult(true,"删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new PygResult(false,"删除失败");
        }
    }

    /**
     * 定义查询请求，查询品牌下拉列表，进行多项选择
     */
    @RequestMapping("findSpecList")
    public List<Map> findSpecList(){
        return specificationService.findSpecList();
    }
}
