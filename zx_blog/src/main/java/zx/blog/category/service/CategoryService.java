package zx.blog.category.service;

import java.util.List;

import zx.blog.category.domain.Category;

public interface CategoryService {
	/**
	 * 查找全部类别
	 * @return
	 */
	public List<Category> findAllCategory();
	
	/**
	 * 添加一个类别
	 * @param category
	 */
	public void addCategory(Category category);

	/**
	 * 更新一个类别
	 * @param category
	 */
	public void updateCategory(Category category);
	
	/**
	 * 根据类别ID移除类别
	 * @param categoryId
	 */
	public void remove(int categoryId);

}
