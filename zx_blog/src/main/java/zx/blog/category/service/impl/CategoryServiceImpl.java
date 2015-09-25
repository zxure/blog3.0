package zx.blog.category.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import zx.blog.category.domain.Category;
import zx.blog.category.service.CategoryService;
import zx.blog.common.SystemContext;
import zx.blog.mapper.ArticleMapper;
import zx.blog.mapper.CategoryMapper;
@Component
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	private CategoryMapper categoryDao;
	@Autowired
	private ArticleMapper articleDao;

	@Override
	public List<Category> findAllCategory() {
		return categoryDao.findAll();
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
}
