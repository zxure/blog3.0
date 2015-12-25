package zx.blog.cache.core;

import java.io.Serializable;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;

import zx.blog.cache.CacheType;
import zx.blog.entity.TableRecordVersion;
import zx.blog.mapper.BaseMapper;

/**
 * 数据缓存管理模板
 * @author Administrator
 *
 * @param <DB_K> 缓存对应的数据库主键类型
 * @param <V> 缓存中所存放的实体的类型
 */
public interface CacheManagerTemplate<DB_K extends Serializable, V extends TableRecordVersion> {
	
	/**
	 * 从数据查询
	 * @param id
	 * @return
	 */
	default V select(DB_K dbKey){
		return this.getCorrespondingMapper().slelectById(dbKey);
	}
	
	/**
	 * 查询缓存
	 * @param cacheKey
	 * @return
	 */
	default Optional<V> find(String cacheKey, DB_K dbKey){
		return this.getFindOpt().apply(cacheKey, dbKey);
	}
	
	/**
	 * 添加到缓存
	 * @param cacheKey
	 * @param cacheValue
	 */
	default void add(V cacheValue){
		this.getAddOpt().accept(cacheValue);
	}
	
	/**
	 * 从缓存中移除
	 * @param cacheKey
	 */
	default void remove(String cacheKey){
		this.getRemoveOpt().accept(cacheKey);
	}
	
	/**
	 * 更新缓存
	 * @param cacheKey
	 * @param cacheValue
	 */
	default void update(V cacheValue){
		this.getUpdateOpt().accept(cacheValue);
	}
	
	/**获得 find 的逻辑处理器*/
	public BiFunction<String, DB_K, Optional<V>> getFindOpt();
	
	/**获得 add 的逻辑处理器*/
	public Consumer<V> getAddOpt();
	
	/**获得 remove 的逻辑处理器*/
	public Consumer<String> getRemoveOpt();
	
	/**获得update的逻辑处理器*/
	public Consumer<V> getUpdateOpt();
	
	/**
	 * 获取对应的唯一 mapper， 由具体子类实现
	 * @return
	 */
	public  BaseMapper<DB_K, V> getCorrespondingMapper();
	
	/**
	 * 获取对应的缓存类型， 由具体实现类实现
	 * @return
	 */
	public CacheType getCacheType();
}
