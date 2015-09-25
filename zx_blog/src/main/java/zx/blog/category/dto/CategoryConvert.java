package zx.blog.category.dto;

import zx.blog.category.domain.Category;

public class CategoryConvert {
	public static SimpleCategoryDto convertToSimpleCategoryDto(Category category){
		return new SimpleCategoryDto(category.getCategoryId(), category.getCategoryName());
	}
}
