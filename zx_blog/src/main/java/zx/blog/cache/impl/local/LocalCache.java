package zx.blog.cache.impl.local;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import zx.blog.cache.Cache;

public class LocalCache<K, V> implements Cache<K, V>{
	private Map<K, V> cache = new ConcurrentHashMap<K, V>();
	@Override
	public void add(K key, V value) {
		cache.put(key, value);
	}

	@Override
	public V get(K key) {
		return cache.get(key);
	}

	@Override
	public void clear() {
		this.cache.clear();
	}

	@Override
	public int size() {
		return cache.size();
	}

	@Override
	public void remove(K key) {
		cache.remove(key);
	}
	
	@Override
	public List<V> getList(){
		List<V> values = new ArrayList<V>();
		for(V value : cache.values()){
			values.add(value);
		}
		return values;
	}
}
