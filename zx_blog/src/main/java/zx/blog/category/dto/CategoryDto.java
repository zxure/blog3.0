package zx.blog.category.dto;

import zx.blog.category.domain.Category;

public class CategoryDto {
	private int categoryId;
	private String categoryName;
	
	public static CategoryDto valueOf(Category category){
		CategoryDto result = new CategoryDto();
		result.categoryId = category.getCategoryId();
		result.categoryName = category.getCategoryName();
		return result;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
}
