package zx.blog.cache.core;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;

import zx.blog.cache.base.accessor.BaseCacheAccessor;
import zx.blog.cache.container.accessor.AccessorContainer;
import zx.blog.cache.container.mapper.MapperContainer;
import zx.blog.cache.schedule.mysqlOpt.MysqlDataOpt;
import zx.blog.cache.schedule.mysqlOpt.OptType;
import zx.blog.entity.TableRecordVersion;
import zx.blog.mapper.BaseMapper;

/**
 * 缓存管理抽象类
 * @author Administrator
 *
 * @param <DB_K> 缓存对应的数据库主键类型
 * @param <V> 缓存中所存放的实体的类型
 */
public abstract class SimpleCacheManager<DB_K extends Serializable, V extends TableRecordVersion> implements CacheManagerTemplate<DB_K, V>{

	@Autowired
	private MapperContainer mapperContainer;
	@Autowired
	private AccessorContainer<V> accessorContainer;
	@Autowired
	private MysqlDataOpt<DB_K, V> mysqlDataOpt;
	
	/**obtain的逻辑处理器  TODO 需要重写*/
	private BiFunction<String, DB_K,  Optional<V>> findOpt = (cacheKey, dbKey)->{
		Optional<V> entityOptional = null;
		//1、先从缓存获取
/*		TODO  修改写法
 * 		accessorContainer.getAccessorList().stream().forEach(accessor -> {
			if(accessor.get(getCacheType(), cacheKey) == null){
				accessor.getLevel();
			}
		});*/
		
		List<BaseCacheAccessor<V>> needToBeSetCacheList = new ArrayList<BaseCacheAccessor<V>>();
		
		for(BaseCacheAccessor<V> baseCacheAccessor : accessorContainer.getAccessorList()){
			if( (entityOptional = baseCacheAccessor.get(getCacheType(), cacheKey)) != null){
				break;
			}
			needToBeSetCacheList.add(baseCacheAccessor);
		}
		
		//缓存有记录
		if(entityOptional.isPresent()){
			return entityOptional;
		}
		
		//2、缓存没有数据，则查询数据库
		entityOptional = Optional.of(this.select(dbKey));
		
		//3、 数据库 有 记录，
		if(entityOptional.isPresent()){
			//设置到缓存中区
			// TODO 修改写法
			//needToBeSetCacheList.stream().forEach(accessor->accessor.set(getCacheType(), cacheKey, entity));
			
			for(BaseCacheAccessor<V> needToSetBaseCacheAccessor : accessorContainer.getAccessorList()){
				needToSetBaseCacheAccessor.set(getCacheType(), cacheKey, entityOptional.get());
			}
			
			return entityOptional;
		}
		
		//数据库也没有记录
		return Optional.empty();
		
	};
	
	private Supplier<List<V>> findAllOpt = ()->{
		Optional<List<V>> result = null;
		
		//TODO  先从缓存查询
		for(BaseCacheAccessor<V> baseCacheAccessor : accessorContainer.getAccessorList()){
			if( (result = baseCacheAccessor.findAll(getCacheType())) != null){
				break;
			}
		}
		
		//缓存不存在，则从数据库查询
		if(!result.isPresent()){
			return this.selectAll();
		}
		
		return null;
	};
	
	/**insert的逻辑处理器*/
	private Consumer<V> addOpt = entity -> accessorContainer.getAccessorList().stream().forEach(accessor->{
		accessor.set(getCacheType(), entity.obtainCacheKey(), entity);
		mysqlDataOpt.addInsertOrUpdate(OptType.ADD, getCacheType(), entity);
	});
	
	/**delete的逻辑处理器*/
	@SuppressWarnings("unchecked")
	private Consumer<String> removeOpt = key -> accessorContainer.getAccessorList().stream().forEach(accessor->{
		accessor.delete(getCacheType(), key);
		mysqlDataOpt.addDelete(OptType.DELETE, getCacheType(), (DB_K) TableRecordVersion.getDbId(key));
	});
	
	/**update的逻辑处理器*/
	private Consumer<V> updateOpt = entity -> {
		accessorContainer.getAccessorList().stream().forEach(accessor->{
			accessor.update(getCacheType(), entity.obtainCacheKey(), entity);
			mysqlDataOpt.addInsertOrUpdate(OptType.ADD, getCacheType(), entity);
		});
		
		
	};

	@Override
	public BiFunction<String, DB_K, Optional<V>> getFindOpt() {
		return findOpt;
	}
	
	@Override
	public Supplier<List<V>> getFindAllOpt(){
		return this.findAllOpt;
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
	
	@Override
	@SuppressWarnings("unchecked")
	public BaseMapper<DB_K, V> getCorrespondingMapper() {
		//return mapperContainer.getMapper((Class<V>)getEntityType());
		return (BaseMapper<DB_K, V>) mapperContainer.getMapper(getCacheType().getClazz());
	}
	
	public Type getEntityType(){
		ParameterizedType parameterized = ((ParameterizedType)(this.getClass().getGenericSuperclass()));
		
		Type[] types = parameterized.getActualTypeArguments();
		
		return types[1];
	}
}
