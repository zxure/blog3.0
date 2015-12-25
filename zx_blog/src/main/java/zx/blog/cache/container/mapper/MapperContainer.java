package zx.blog.cache.container.mapper;

import java.io.Serializable;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import zx.blog.cache.container.BaseContainer;
import zx.blog.entity.TableRecordVersion;
import zx.blog.mapper.BaseMapper;
import zx.blog.util.StringUtils;

@Component
public class MapperContainer extends BaseContainer{
	
	private static final String MAPPER_NAME_SUFFIX = "Mapper";

	@SuppressWarnings("rawtypes")
	private Map<String, BaseMapper> mapperMap;
	
	@PostConstruct
	public void init(){
		mapperMap = applicationContext.getBeansOfType(BaseMapper.class);
	}
	
	/**
	 * 获取对应 class的 数据库操作mapper
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <K extends Serializable, T extends TableRecordVersion> BaseMapper<K, T> getMapper(Class<T> clazz){
		
		String mapperName = StringUtils.uncapitalize(clazz.getSimpleName()) + MAPPER_NAME_SUFFIX;
		
		return mapperMap.get(mapperName);
	}
}
