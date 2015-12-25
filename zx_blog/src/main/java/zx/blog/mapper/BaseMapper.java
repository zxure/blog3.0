package zx.blog.mapper;

import java.io.Serializable;

import zx.blog.entity.TableRecordVersion;

/**
 * 数据库父接口
 * @author Administrator
 *
 * @param <DB_K> 数据库表 主键 类型
 * @param <T>	数据库表 锁映射的 实体类型
 */
public interface BaseMapper<DB_K extends Serializable, T extends TableRecordVersion> {
	
	/**
	 * 插入到数据库
	 * @param entity
	 */
	public void insert(T entity);
	
	/**
	 * 根据ID 查询数据库
	 * @param id
	 * @return
	 */
	public T slelectById(DB_K id);
	
	/**
	 * 根据ID 更新数据库记录
	 * @param id
	 * @param entity
	 */
	public void update(T entity);
	
	/**
	 * 根据ID删除
	 * @param id
	 */
	public void delete(DB_K id);

}
