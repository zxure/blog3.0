package zx.blog.cache.core.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import zx.blog.cache.CacheType;
import zx.blog.cache.container.mapper.MapperContainer;
import zx.blog.cache.core.SimpleCacheManager;
import zx.blog.mapper.BaseMapper;
import zx.blog.user.domain.User;

@Component
public class UserCacheManager extends SimpleCacheManager<Integer, User>{

	@Autowired
	private MapperContainer mapperContainer;
	
	@Override
	public BaseMapper<Integer, User> getCorrespondingMapper() {
		return mapperContainer.getMapper(User.class);
	}

	@Override
	public CacheType getCacheType() {
		return CacheType.USER;
	}

}
