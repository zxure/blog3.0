package zx.blog.category.dao;

import java.util.List;

import zx.blog.category.domain.Category;

public interface CategoryDao {
	/**
	 * 查询全部的类别
	 * @return
	 */
	public List<Category> findAll();

	/**
	 * 增加类别
	 * @param category
	 */
	public void insert(Category category);

	/**
	 * 更新
	 * @param category
	 */
	public void update(Category category);
	
	/**
	 * 删除类别
	 * @param categoryId
	 */
	public void delete(int categoryId);
}
