package zx.blog.cache.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;

import zx.blog.cache.base.accessor.BaseCacheAccessor;
import zx.blog.cache.container.accessor.AccessorContainer;
import zx.blog.entity.TableRecordVersion;

/**
 * 缓存管理抽象类
 * @author Administrator
 *
 * @param <DB_K> 缓存对应的数据库主键类型
 * @param <V> 缓存中所存放的实体的类型
 */
public abstract class SimpleCacheManager<DB_K extends Serializable, V extends TableRecordVersion> implements CacheManagerTemplate<DB_K, V>{

	@Autowired
	private AccessorContainer<V> accessorContainer;
	
	/**obtain的逻辑处理器  TODO 需要重写*/
	private BiFunction<String, DB_K,  Optional<V>> findOpt = (cacheKey, dbKey)->{
		V entity = null;
		//1、先从缓存获取
/*		TODO  修改写法
 * 		accessorContainer.getAccessorList().stream().forEach(accessor -> {
			if(accessor.get(getCacheType(), cacheKey) == null){
				accessor.getLevel();
			}
		});*/
		
		List<BaseCacheAccessor<V>> needToBeSetCacheList = new ArrayList<BaseCacheAccessor<V>>();
		
		for(BaseCacheAccessor<V> baseCacheAccessor : accessorContainer.getAccessorList()){
			if( (entity = baseCacheAccessor.get(getCacheType(), cacheKey)) == null){
				break;
			}
			needToBeSetCacheList.add(baseCacheAccessor);
		}
		
		//2、缓存没有查询数据库
		if(entity == null){
			entity = this.select(dbKey);
			
			//3、 数据库 有 记录，
			if(entity != null){
				//设置到缓存中区
				// TODO 修改写法
				//needToBeSetCacheList.stream().forEach(accessor->accessor.set(getCacheType(), cacheKey, entity));
				
				for(BaseCacheAccessor<V> needToSetBaseCacheAccessor : accessorContainer.getAccessorList()){
					needToSetBaseCacheAccessor.set(getCacheType(), cacheKey, entity);
				}
				
				return Optional.of(entity);
			}
		}
		
		return Optional.empty();
	};
	
	/**insert的逻辑处理器*/
	private Consumer<V> addOpt = entity -> accessorContainer.getAccessorList().stream().forEach(accessor->accessor.set(getCacheType(), entity.obtainCacheKey(), entity));
	
	/**delete的逻辑处理器*/
	private Consumer<String> removeOpt = key -> accessorContainer.getAccessorList().stream().forEach(accessor->accessor.delete(getCacheType(), key));
	
	/**update的逻辑处理器*/
	private Consumer<V> updateOpt = entity -> accessorContainer.getAccessorList().stream().forEach(accessor->accessor.update(getCacheType(), entity.obtainCacheKey(), entity));

	@Override
	public BiFunction<String, DB_K, Optional<V>> getFindOpt() {
		return findOpt;
	}

	@Override
	public Consumer<V> getAddOpt() {
		return addOpt;
	}

	@Override
	public Consumer<String> getRemoveOpt() {
		return removeOpt;
	}

	@Override
	public Consumer<V> getUpdateOpt() {
		return updateOpt;
	}
}
