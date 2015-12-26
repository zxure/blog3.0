package zx.blog.cache.redis.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.function.Consumer;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.blog.cache.redis.config.RedisConfigWithLevelAndOpen;

public class RedisConfigParser {
	
	private static final Logger logger = LoggerFactory.getLogger(RedisConfigParser.class);
	
	private RedisConfigParser(){}
	
	private static final RedisConfigParser instance = new RedisConfigParser();
	
	public static RedisConfigParser getInstance(){
		return instance;
	}
	
	private String configFileName = System.getProperty("redis.config.path", "props/redis.properties");
	
	/**
	 * 解析redis properties配置文件
	 * @param configFileName
	 * @return
	 */
	public RedisConfigWithLevelAndOpen parseConfig(){
		
		Properties properties = new Properties();
		
		InputStream ins = RedisConfigParser.class.getClassLoader().getResourceAsStream(configFileName);
		
		if(ins != null){
			try{
				properties.load(ins);
			} catch(IOException e){
				logger.error("load jedis properties["+ configFileName +"] from classpath failed", e);
			} finally{
				try {
					ins.close();
				} catch (IOException e) {
					//e.printStackTrace();
				}
			}
		}
		
		RedisConfigWithLevelAndOpen config = new RedisConfigWithLevelAndOpen();
		config.setHost("127.0.0.1");
		
		this.setPropertyToConfig(properties, config);
		
		return config;
	}

	//把属性值设置到config中
	private void setPropertyToConfig(Properties properties, RedisConfigWithLevelAndOpen config) {
		if(properties == null){
			return ;
		}
		
		MetaObject metaObjects = SystemMetaObject.forObject(config);

		Consumer<Map.Entry<Object, Object>> setterFunction = e -> {
			String key = (String) e.getKey();
			String value = (String)e.getValue();
			Class<?> type = metaObjects.getSetterType(key);
			
			if(type == int.class){
				metaObjects.setValue(key, Integer.valueOf(value));
			} else if(type == long.class){
				metaObjects.setValue(key, Long.valueOf(value));
			} else if(type == short.class){
				metaObjects.setValue(key, Short.valueOf(value));
			} else if(type == byte.class){
				metaObjects.setValue(key, Byte.valueOf(value));
			} else if(type == double.class){
				metaObjects.setValue(key, Double.valueOf(value));
			} else if(type == float.class){
				metaObjects.setValue(key, Float.valueOf(value));
			} else if(type == boolean.class){
				metaObjects.setValue(key, Boolean.valueOf(value));
			}
		};
		properties.entrySet().stream().filter(e->metaObjects.hasSetter((String)e.getKey())).forEach(setterFunction);
	}
	
	
}
