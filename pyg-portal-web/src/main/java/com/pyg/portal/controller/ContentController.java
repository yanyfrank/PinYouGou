package com.pyg.portal.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pyg.content.service.ContentService;
import com.pyg.pojo.TbContent;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/content")
public class ContentController {

    //注入service
    @Reference(timeout = 10000000)
    private ContentService contentService;

    /**
     * 需求：根据广告分类id查询广告内容
     * 参数： long categoryid
     * 返回值：list《tbcontent》
     * 开发一个restfull风格接口
     */
    @RequestMapping("findContentListByCategoryId/{categoryId}")
    public List<TbContent> findContentListByCategoryId(@PathVariable Long categoryId){
        //调用远程服务service方法
        return contentService.findContentListByCategoryId(categoryId);
    }
}
