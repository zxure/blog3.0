package zx.blog.cache.schedule.mysqlOpt;

import java.io.Serializable;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import zx.blog.cache.CacheType;
import zx.blog.cache.container.mapper.MapperContainer;
import zx.blog.entity.TableRecordVersion;
import zx.blog.mapper.BaseMapper;

@Component
public class MysqlDataOpt<DB_K extends Serializable, V extends TableRecordVersion> {
	
	private static final Logger logger = LoggerFactory.getLogger(MysqlDataOpt.class);
	
	@Autowired
	private MapperContainer mapperContainer;
	
	//定时插入集合
	private ConcurrentHashMap<CacheType, ConcurrentLinkedQueue<V>> insertLater = new ConcurrentHashMap<CacheType, ConcurrentLinkedQueue<V>>();
	
	//定时更新集合
	private ConcurrentHashMap<CacheType, ConcurrentLinkedQueue<V>> updateLater = new ConcurrentHashMap<CacheType, ConcurrentLinkedQueue<V>>();
	
	//定时删除集合
	private ConcurrentHashMap<CacheType, ConcurrentLinkedQueue<DB_K>> deleteLater = new ConcurrentHashMap<CacheType, ConcurrentLinkedQueue<DB_K>>();
	
	@PostConstruct
	public void init(){
		Arrays.stream(CacheType.values()).forEach(cacheType -> {
			insertLater.put(cacheType, new ConcurrentLinkedQueue<V>());
			updateLater.put(cacheType, new ConcurrentLinkedQueue<V>());
			deleteLater.put(cacheType, new ConcurrentLinkedQueue<DB_K>());
		});
	}
	
	/**
	 * 每隔十分钟保存一次数据库
	 */
	@Scheduled(cron="0 0/10 * * * ?")
	@SuppressWarnings("unchecked")
	public void oprateMySql(){
		insertLater.entrySet().stream().forEach(entry->{
			CacheType cacheType = entry.getKey();
			ConcurrentLinkedQueue<V> queue = entry.getValue();
			BaseMapper<? extends Serializable, V> mapper = (BaseMapper<? extends Serializable, V>) mapperContainer.getMapper(cacheType.getClazz());
			queue.stream().forEach(mapper::insert);
			queue.clear();
		});

		updateLater.entrySet().stream().forEach(entry->{
			CacheType cacheType = entry.getKey();
			ConcurrentLinkedQueue<V> queue = entry.getValue();
			BaseMapper<? extends Serializable, V> mapper = (BaseMapper<? extends Serializable, V>) mapperContainer.getMapper(cacheType.getClazz());
			queue.stream().forEach(mapper::update);
			queue.clear();
		});
		
		deleteLater.entrySet().stream().forEach(entry->{
			CacheType cacheType = entry.getKey();
			ConcurrentLinkedQueue<? extends Serializable> queue = entry.getValue();
			BaseMapper<? extends Serializable, V> mapper = (BaseMapper<? extends Serializable, V>) mapperContainer.getMapper(cacheType.getClazz());
			queue.stream().forEach(mapper::delete);
			queue.clear();
		});
		
		
	}
	
	/**
	 * 增加对应的记录到待操作集合
	 * @param optType	操作类型
	 * @param catchType	缓存类型
	 * @param entity	实体
	 */
	public void addInsertOrUpdate(OptType optType, CacheType catchType, V entity){
		switch(optType){
			case ADD:
				insertLater.get(catchType).add(entity);
			case UPDATE:
				updateLater.get(catchType).add(entity);
			default:
				logger.info("不支持的类型...." + optType.name());
		}
	}
	
	public void addDelete(OptType optType, CacheType catchType, DB_K dbKey){
		switch(optType){
		case DELETE:
			deleteLater.get(catchType).add(dbKey);
		default:
			logger.info("不支持的类型...." + optType.name());
		}
	}
}
