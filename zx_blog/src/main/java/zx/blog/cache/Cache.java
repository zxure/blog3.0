package zx.blog.cache;

import java.util.List;

/**
 * 缓存
 * @author wqr503
 *
 */
public interface Cache<K, V> {
	/**
	 * 设置缓存
	 * @param key
	 * @param value
	 */
	public void add(K key, V value);
	
	/**
	 * 取得缓存
	 * @param key
	 * @return
	 */
	public V get(K key);
	
	/**
	 * 清空缓存
	 */
	public void clear();
	
	/**
	 * 拿到当前容量
	 */
	public int size();
	
	/**
	 * 移除缓存
	 * @param key
	 */
	public void remove(K key);

	/**
	 * 获取所有值
	 * @return
	 */
	public List<V> getList();
}
