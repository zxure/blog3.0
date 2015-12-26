package zx.blog.category.service;

import java.util.List;

import zx.blog.category.domain.Category;
import zx.blog.category.dto.CategoryDto;

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

	/**
	 * 判断类别是否存在
	 * @param categoryName
	 * @return
	 */
	public boolean isExistCategory(String categoryName);

	/**
	 * 根据ID查询一个类别
	 * @param categoryId
	 * @return
	 */
	public Category findById(int categoryId);

	/**
	 * 获取所有的 categoryDto
	 * @return
	 */
	public List<CategoryDto> getAllCategoryDtoList();

}
