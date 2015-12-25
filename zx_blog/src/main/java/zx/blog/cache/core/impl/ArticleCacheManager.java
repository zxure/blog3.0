package zx.blog.cache.core.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import zx.blog.article.domain.Article;
import zx.blog.cache.CacheType;
import zx.blog.cache.container.mapper.MapperContainer;
import zx.blog.cache.core.SimpleCacheManager;
import zx.blog.mapper.BaseMapper;

@Component
public class ArticleCacheManager extends SimpleCacheManager<Integer, Article>{

	@Autowired
	private MapperContainer mapperContainer;
	
	@Override
	public BaseMapper<Integer, Article> getCorrespondingMapper() {
		return mapperContainer.getMapper(Article.class);
	}

	@Override
	public CacheType getCacheType() {
		return CacheType.ARTICLE;
	}

}
