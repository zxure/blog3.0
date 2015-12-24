package zx.blog.cache.core.accessor;

import java.util.function.Function;

import javax.annotation.PostConstruct;

import org.mybatis.caches.redis.ConfigWithHost;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import zx.blog.cache.CacheType;
import zx.blog.cache.config.RedisConfig;

/**
 * redis缓存访问接口
 * @author Administrator
 *
 */

@Component
public class RedisCacheAccessor {
	
	private JedisPool pool;
	
	@Value("")
	private boolean isRedisCacheOpen = false;
	
	@PostConstruct
	public void init(){
		if(isRedisCacheOpen){
			ConfigWithHost config = RedisConfig.getInstance().parseConfig();
			
			pool = new JedisPool(config, config.getHost());
		}
	}
	
	public Object execute(Function<Jedis, Object> redisOpt){
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
	public byte[] get(CacheType type, byte[] key){
		return (byte[]) this.execute(jedis->jedis.hget(type.name().getBytes(), key));
	}
	
}
