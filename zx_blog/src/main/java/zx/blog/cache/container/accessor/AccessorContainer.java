package zx.blog.cache.container.accessor;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import zx.blog.cache.base.accessor.BaseCacheAccessor;
import zx.blog.cache.container.BaseContainer;
import zx.blog.entity.TableRecordVersion;

@Component
public class AccessorContainer<V extends TableRecordVersion> extends BaseContainer {
	/**所有的 CacheAccessor */
	private List<BaseCacheAccessor<V>> accessorList;
	
	@PostConstruct
	public void init(){
		this.accessorList = this.applicationContext.getBeansOfType(BaseCacheAccessor.class).entrySet().stream()
				.filter(accessor -> accessor.getValue().isCacheOpen())
				.map(Map.Entry::getValue)
				.sorted(Comparator.comparing(BaseCacheAccessor::getLevel))
				.collect(Collectors.toList());
	}

	public List<BaseCacheAccessor<V>> getAccessorList() {
		return accessorList;
	}
}
