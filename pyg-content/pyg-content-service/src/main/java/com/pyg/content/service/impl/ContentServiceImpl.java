package com.pyg.content.service.impl;
import java.util.List;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pyg.mapper.TbContentMapper;
import com.pyg.pojo.TbContent;
import com.pyg.pojo.TbContentExample;
import com.pyg.pojo.TbContentExample.Criteria;
import com.pyg.content.service.ContentService;

import com.pyg.uitls.PageResult;
import org.springframework.data.redis.core.RedisTemplate;


/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper contentMapper;

	//注入redis模板对象
	@Autowired
	private RedisTemplate redisTemplate;
	
	/**
	 * 查询全部
	 */
	@Override
	public List<TbContent> findAll() {
		return contentMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		Page<TbContent> page=   (Page<TbContent>) contentMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 增加
	 */
	@Override
	public void add(TbContent content) {


		//先删除缓存数据
		redisTemplate.boundHashOps("INDEX_CACHE").delete(content.getCategoryId());

		contentMapper.insert(content);		
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(TbContent content){
		//先根据id查询内容数据
		TbContent tbContent = contentMapper.selectByPrimaryKey(content.getId());

		//先删除缓存
		redisTemplate.boundHashOps("INDEX_CACHE").delete(tbContent.getCategoryId());

		contentMapper.updateByPrimaryKey(content);
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public TbContent findOne(Long id){
		return contentMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		for(Long id:ids){
			//查询
			TbContent tbContent = contentMapper.selectByPrimaryKey(id);
			//删除缓存
			redisTemplate.boundHashOps("INDEX_CACHE").delete(tbContent.getCategoryId());
			contentMapper.deleteByPrimaryKey(id);
		}		
	}
	
	
		@Override
	public PageResult findPage(TbContent content, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbContentExample example=new TbContentExample();
		Criteria criteria = example.createCriteria();
		
		if(content!=null){			
						if(content.getTitle()!=null && content.getTitle().length()>0){
				criteria.andTitleLike("%"+content.getTitle()+"%");
			}
			if(content.getUrl()!=null && content.getUrl().length()>0){
				criteria.andUrlLike("%"+content.getUrl()+"%");
			}
			if(content.getPic()!=null && content.getPic().length()>0){
				criteria.andPicLike("%"+content.getPic()+"%");
			}
			if(content.getContent()!=null && content.getContent().length()>0){
				criteria.andContentLike("%"+content.getContent()+"%");
			}
			if(content.getStatus()!=null && content.getStatus().length()>0){
				criteria.andStatusLike("%"+content.getStatus()+"%");
			}
	
		}
		
		Page<TbContent> page= (Page<TbContent>)contentMapper.selectByExample(example);		
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 需求：根据广告分类id查询广告内容
	 * @param categoryId
	 * @return
	 * 业务分析
	 * 此方法是查询广告数据的方法，首页面临高并发的访问，广告数据的查询也会面临高并发的压力
	 * 随着用户的增多，压力将会越来越大，数据库压力也会非常大，甚至会导致数据的崩溃
	 * 缓存服务器：redis
	 * 缓存数据结构：hash
	 * key:INDEX_CACHE FOOD_CACHE
	 * field:categoryId
	 * value:广告数据
	 * 缓存业务：
	 * 1，每次查询广告数据，先查询广告缓存数据
	 * 2，如果缓存没有数据，再次查询数据库数据，同时把数据给redis缓存
	 * 3，如果缓存中又数据，就不用查询数据库数据，直接返回即可
	 */
	@Override
	public List<TbContent> findContentListByCategoryId(Long categoryId) {

		try {
			//首先查询广告缓存数据
			String addJson = (String) redisTemplate.boundHashOps("INDEX_CACHE").get(categoryId);
			//判断广告缓存数据是否存在
			if(addJson != null && !"".equals(addJson)){
                //把json字符串转换json集合对象
                List<TbContent> contentList = JSON.parseArray(addJson, TbContent.class);
                return contentList;
            }
		} catch (Exception e) {
			e.printStackTrace();
		}


		//创建广告内容example对象
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		//设置查询参数，根据外键查询
		criteria.andCategoryIdEqualTo(categoryId);
		//执行查询
		List<TbContent> tbContents = contentMapper.selectByExample(example);

		//把查询的数据库的广告数据放入redis缓存
		redisTemplate.boundHashOps("INDEX_CACHE").put(categoryId,JSON.toJSONString(tbContents));

		//返回
		return tbContents;
	}

}
