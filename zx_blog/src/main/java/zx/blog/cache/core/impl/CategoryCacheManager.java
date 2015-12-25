package zx.blog.cache.core.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import zx.blog.cache.CacheType;
import zx.blog.cache.container.mapper.MapperContainer;
import zx.blog.cache.core.SimpleCacheManager;
import zx.blog.category.domain.Category;
import zx.blog.mapper.BaseMapper;

@Component
public class CategoryCacheManager extends SimpleCacheManager<Integer, Category>{

	@Autowired
	private MapperContainer mapperContainer;
	
	@Override
	public BaseMapper<Integer, Category> getCorrespondingMapper() {
		return mapperContainer.getMapper(Category.class);
	}

	@Override
	public CacheType getCacheType() {
		return CacheType.CATEGORY;
	}

}
