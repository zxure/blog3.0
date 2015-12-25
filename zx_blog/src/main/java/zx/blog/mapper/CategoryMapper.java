package zx.blog.mapper;

import java.util.List;

import zx.blog.category.domain.Category;

public interface CategoryMapper extends BaseMapper<Integer, Category>{
	/**
	 * 查询全部的类别
	 * @return
	 */
	public List<Category> findAll();
}
