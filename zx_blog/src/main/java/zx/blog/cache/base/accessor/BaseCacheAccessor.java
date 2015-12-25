package zx.blog.cache.base.accessor;

import zx.blog.cache.CacheType;
import zx.blog.cache.base.config.BaseConfig;
import zx.blog.entity.TableRecordVersion;

/**
 * 缓存访问接口
 * @author Administrator
 */
public interface BaseCacheAccessor<V extends TableRecordVersion> {
	
	/**
	 * 获取缓存
	 * @param type {@link CacheType} 类型
	 * @param cacheKey 
	 * @return
	 */
	public V get(CacheType type, String cacheKey);
	
	/**
	 * 设置到缓存
	 * @param type
	 * @param cacheKey
	 * @param value
	 */
	public void set(CacheType type, String cacheKey, V value);
	
	/**
	 * 删除缓存
	 * @param type
	 * @param fieldKey
	 */
	public void delete(CacheType type, String fieldKey);
	
	/**
	 * 更新缓存
	 * @param type
	 * @param cacheKey
	 * @param value
	 */
	public void update(CacheType type, String cacheKey, V value);
	
	/**
	 * 获取缓存配置
	 */
	public BaseConfig getConfig();
	
	/**
	 * 缓存是否开启
	 * @return
	 */
	default boolean isCacheOpen(){
		return this.getConfig().isCacheOpen();
	}
	


	/**
	 * 获取缓存的级数，1级最高
	 * @return
	 */
	default int getLevel(){
		return this.getConfig().getLevel();
	}
}
