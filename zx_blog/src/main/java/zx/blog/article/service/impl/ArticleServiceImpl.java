package zx.blog.article.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.htmlparser.util.ParserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import zx.blog.article.domain.Article;
import zx.blog.article.dto.SimpleArticleDto;
import zx.blog.article.service.ArticleService;
import zx.blog.article.utils.ArticleBriefTools;
import zx.blog.cache.container.CacheHolder;
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
	
	private static final int ARTICLE_BRIEF_LENGTH = 300;

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
	public List<Article> getArticlesByPage(int pageNum) {
		Map<String, Integer> map = PageUtils.getNextArticlePage(pageNum);
		int start = map.get("start");
		int end = map.get("end");
		List<Article> articles = articleDao.findOnePageArticle(start, end);
		List<Article> articleDtos = new ArrayList<Article>();
		for(Article article : articles){
			try {
				article.setBriefContent(ArticleBriefTools.getBrief(article.getContent(), ARTICLE_BRIEF_LENGTH));
			} catch (ParserException e) {
				e.printStackTrace();
			}
		}
		return articleDtos;
	}
	
	@Override
	public int getArticleTotalNum(){
		return articleDao.getArticleTotalNum();
	}
	
	@Override
	public Article viewArticleById(int articleId) {
		//修改浏览次数
		Article article = this.getArticleById(articleId);
		article.setTotalViewTimes(article.getTotalViewTimes() + 1);
		article.setDayViewTimes(article.getDayViewTimes() + 1);
		article.setWeekViewTimes(article.getWeekViewTimes() + 1);
		article.setMonthViewTimes(article.getMonthViewTimes() + 1);
		this.updateArticle(article);
		return article;
	}

	@Override
	public Article getArticleDtoById(int articleId) {
		return this.articleDao.findById(articleId);
	}
	
	@Override
	public Article getArticleById(int articleId){
		return this.articleDao.findById(articleId);
	}

	@Override
	public void updateArticle(Article article) {
		this.articleDao.update(article);
	}

	@Override
	public List<SimpleArticleDto> getAllSimpleArticleDto() {
		List<Article> articles = this.articleDao.findAll();
		List<SimpleArticleDto> simpleArticleDtos = new ArrayList<SimpleArticleDto>();
		for(Article article : articles)
			simpleArticleDtos.add(null);
		return simpleArticleDtos;
	}

	@Override
	public List<Article> getArticleByCategory(int categoryId) {
		List<Article> articles = this.articleDao.findByCategoryId(categoryId);
		for(Article article : articles){
			try {
				article.setBriefContent(ArticleBriefTools.getBrief(article.getContent(), ARTICLE_BRIEF_LENGTH));
			} catch (ParserException e) {
				e.printStackTrace();
			}
		}
		return articles;
	}
	
	@Override
	public void deleteArticle(int articleId){
		this.articleDao.deleteArticleByArticleId(articleId);
	}
}
