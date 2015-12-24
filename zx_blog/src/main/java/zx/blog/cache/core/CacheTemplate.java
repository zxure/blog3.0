package zx.blog.cache.core;

import zx.blog.entity.TableRecordVersion;

public interface CacheTemplate<V extends TableRecordVersion> {
	
	/**
	 * 从数据查询
	 * @param id
	 * @return
	 */
	public TableRecordVersion select(int id);
	
	/**
	 * 从缓存查询
	 * @param id
	 * @return
	 */
	public V obtain(int id);
	
	/**
	 * 插入
	 * @param entity
	 */
	public void insert(V entity);
	
	/**
	 * 删除
	 * @param id
	 */
	public void delete(int id);

	/**
	 * 更新
	 * @param entity
	 */
	public void update(V entity);
}
