package zx.blog.cache.redis.accessor;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.mybatis.caches.redis.SerializeUtil;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import zx.blog.cache.CacheType;
import zx.blog.cache.base.accessor.BaseCacheAccessor;
import zx.blog.cache.base.config.BaseConfig;
import zx.blog.cache.redis.config.RedisConfigWithLevelAndOpen;
import zx.blog.cache.redis.utils.RedisConfigParser;
import zx.blog.entity.TableRecordVersion;

/**
 * redis缓存访问接口
 * @author Administrator
 */
@Component
public class RedisCacheAccessor<V extends TableRecordVersion> implements BaseCacheAccessor<V>{
	
	private JedisPool pool;
	
	private RedisConfigWithLevelAndOpen config;
	
	@PostConstruct
	public void init(){
		config = RedisConfigParser.getInstance().parseConfig();
		
		if(this.isCacheOpen()){
			pool = new JedisPool(config, config.getHost());
		} else {
			config = null;
		}
		
	}
	
	/**
	 * 执行逻辑
	 * @param redisOpt
	 * @return
	 */
	private Object execute(Function<Jedis, Object> redisOpt){
		Jedis jedis = null;
		try{
			jedis = this.pool.getResource();
			
			return redisOpt.apply(jedis);
		}
		finally{
			jedis.close();
		}
	}
	
	/**
	 * 获取缓存
	 * @param type {@link CacheType} 类型
	 * @param key 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Optional<V> get(CacheType type, String cacheKey){
		V entity = (V) SerializeUtil.unserialize((byte[]) this.execute(jedis->jedis.hget(type.getKeyBytes(), cacheKey.getBytes())));
		return Optional.of(entity);
	}
	
	/**
	 * 设置到缓存
	 * @param type
	 * @param fieldKey
	 * @param fieldValue
	 */
	@Override
	public void set(CacheType type, String cacheKey, V value){
		this.execute(jedis->jedis.hset(type.getKeyBytes(), cacheKey.getBytes(), SerializeUtil.serialize(value)));
	}
	
	/**
	 * 删除缓存
	 * @param type
	 * @param fieldKey
	 */
	@Override
	public void delete(CacheType type, String fieldKey){
		this.execute(jedis->jedis.hdel(type.getKeyBytes(), fieldKey.getBytes()));
	}

	@Override
	public void update(CacheType type, String cacheKey, V value) {
		this.execute(jedis->jedis.hset(type.getKeyBytes(), cacheKey.getBytes(), SerializeUtil.serialize(value)));
	}

	@Override
	@SuppressWarnings("unchecked")
	public Optional<List<V>> findAll(CacheType type) {
		Stream<V> streamV = (Stream<V>) this.execute(jedis->jedis.hgetAll(type.getKeyBytes()).entrySet()
				.stream().map(entry->{
					return (V)SerializeUtil.unserialize(entry.getValue());
			}));//.collect(Collectors.toList()));
		if(streamV.count() <= 0){
			return Optional.empty();
		} else {
			return Optional.of(streamV.collect(Collectors.toList()));
		}
	}

	@Override
	public void clear(CacheType type) {
		this.execute(jedis->jedis.del(type.getKeyBytes()));
	}
	

	@Override
	public boolean isCacheOpen() {
		return config.isCacheOpen();
	}

	@Override
	public int getLevel() {
		return config.getLevel();
	}

	@Override
	public BaseConfig getConfig() {
		return this.config;
	}


}
