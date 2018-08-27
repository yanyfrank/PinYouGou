package com.pyg.search.service;

import java.util.Map;

public interface SearchService {

    /**
     * 需求：根据查询的关键词，搜索商品数据
     * 参数：map searchMap
     * 返回值：map
     */
    Map searchList(Map searchMap);
}
