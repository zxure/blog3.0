package zx.blog.category.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import zx.blog.cache.core.impl.CategoryCacheManager;
import zx.blog.category.domain.Category;
import zx.blog.category.dto.CategoryDto;
import zx.blog.category.service.CategoryService;
import zx.blog.mapper.ArticleMapper;
import zx.blog.mapper.CategoryMapper;
import zx.blog.system.SystemContext;
@Component
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	private CategoryCacheManager categoryCacheManager;
	@Autowired
	private CategoryMapper categoryDao;
	@Autowired
	private ArticleMapper articleDao;
	

	@Override
	public List<Category> findAllCategory() {
		return categoryCacheManager.findAll();
	}

	@Override
	public void addCategory(Category category) {
		categoryDao.insert(category);
	}

	@Override
	public void updateCategory(Category category) {
		categoryDao.update(category);
	}
	
	@Override
	public void remove(int categoryId) {
		//删除该类别下的所有文章
		this.articleDao.deleteArticleByCategoryId(categoryId);
		//删除该类别
		this.categoryDao.delete(categoryId);
		//重新设置文章数目
		SystemContext.articleTotalNum.set(articleDao.getArticleTotalNum());
	}

	@Override
	public boolean isExistCategory(String categoryName) {
		return this.findAllCategory().stream().anyMatch(category->category.getCategoryName().equalsIgnoreCase(categoryName));
	}

	@Override
	public Category findById(int categoryId) {
		return this.categoryCacheManager.find(Category.genKen(categoryId), categoryId).get();
	}

	@Override
	public List<CategoryDto> getAllCategoryDtoList() {
		return this.findAllCategory().stream().map(CategoryDto::valueOf).collect(Collectors.toList());
	}
}
