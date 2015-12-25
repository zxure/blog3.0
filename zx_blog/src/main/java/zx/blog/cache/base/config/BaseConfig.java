package zx.blog.cache.base.config;

public interface BaseConfig {

	/**
	 * 缓存是否开启
	 * @return
	 */
	boolean isCacheOpen();

	/**
	 * 获取缓存级别
	 * @return
	 */
	int getLevel();

}
