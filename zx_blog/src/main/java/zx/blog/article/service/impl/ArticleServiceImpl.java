package zx.blog.article.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import zx.blog.article.domain.Article;
import zx.blog.article.dto.ArticleDto;
import zx.blog.article.service.ArticleService;
import zx.blog.cache.CacheHolder;
import zx.blog.category.domain.Category;
import zx.blog.common.SystemContext;
import zx.blog.mapper.ArticleMapper;
import zx.blog.mapper.CategoryMapper;
import zx.blog.util.PageUtils;

@Component
public class ArticleServiceImpl implements ArticleService{
	@Autowired
	private ArticleMapper articleDao;
	@Autowired
	private CategoryMapper categoryDao;

	@Override
	public boolean addArticle(final Article article) {
		this.articleDao.insert(article);
		SystemContext.articleTotalNum.incrementAndGet();
		int categoryId = article.getCategoryId();
		Category category = CacheHolder.getCagetoryById(categoryId);
		category.setTotalArticleNum(category.getTotalArticleNum() + 1);
		this.categoryDao.update(category);
		return true;
	}

	@Override
	public List<ArticleDto> getArticlesByPage(int pageNum) {
		Map<String, Integer> map = PageUtils.getNextArticlePage(pageNum);
		int start = map.get("start");
		int end = map.get("end");
		List<ArticleDto> articleDtos = articleDao.findOnePageArticle(start, end).stream()
				.map(articleBriefDtoCreatorFunction)
				.collect(Collectors.toList());
		return articleDtos;
	}
	
	@Override
	public int getArticleTotalNum(){
		return articleDao.getArticleTotalNum();
	}
	
	@Override
	public ArticleDto viewArticleById(int articleId) {
		//修改浏览次数
		Article article = this.getArticleById(articleId);
		article.setTotalViewTimes(article.getTotalViewTimes() + 1);
		this.updateArticle(article);
		return ArticleDto.valueOf(article);
	}

	@Override
	public ArticleDto getArticleDtoById(int articleId) {
		Article article= this.articleDao.slelectById(articleId);
		return ArticleDto.valueOf(article);
	}
	
	@Override
	public Article getArticleById(int articleId){
		return this.articleDao.slelectById(articleId);
	}

	@Override
	public void updateArticle(Article article) {
		this.articleDao.update(article);
	}

	@Override
	public List<ArticleDto> getAllArticleDto() {
		List<ArticleDto> articleDtos = this.articleDao.findAll().stream()
				.map(articleBriefDtoCreatorFunction)
				.collect(Collectors.toList());
		return articleDtos;
	}

	@Override
	public List<ArticleDto> getArticleByCategory(int categoryId) {
		List<ArticleDto> articleDtos = this.articleDao.findByCategoryId(categoryId).stream()
				.map(articleBriefDtoCreatorFunction)
				.collect(Collectors.toList());
		return articleDtos;
	}
	
	@Override
	public void deleteArticle(int articleId){
		Article article = this.articleDao.slelectById(articleId);
		Category category = CacheHolder.getCagetoryById(article.getCategoryId());
		this.articleDao.delete(articleId);
		category.setTotalArticleNum(category.getTotalArticleNum() - 1);
		this.categoryDao.update(category);
	}
}
