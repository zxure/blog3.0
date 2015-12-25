package zx.blog.cache.redis.config;

import org.mybatis.caches.redis.ConfigWithHost;

import zx.blog.cache.base.config.BaseConfig;

public class RedisConfigWithLevelAndOpen extends ConfigWithHost implements BaseConfig{
	private int level;
	
	private boolean isCacheOpen;

	@Override
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Override
	public boolean isCacheOpen() {
		return isCacheOpen;
	}

	public void setCacheOpen(boolean isCacheOpen) {
		this.isCacheOpen = isCacheOpen;
	}
	
	
}
