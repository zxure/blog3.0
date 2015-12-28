package zx.blog.cache.core.impl;

import org.springframework.stereotype.Component;

import zx.blog.article.domain.Article;
import zx.blog.cache.CacheType;
import zx.blog.cache.core.SimpleCacheManager;

@Component
public class ArticleCacheManager extends SimpleCacheManager<Integer, Article>{
	
	@Override
	public CacheType getCacheType() {
		return CacheType.ARTICLE;
	}
}
