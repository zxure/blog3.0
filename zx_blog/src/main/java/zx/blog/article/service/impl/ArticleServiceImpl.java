package zx.blog.article.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import zx.blog.article.domain.Article;
import zx.blog.article.dto.ArticleDto;
import zx.blog.article.service.ArticleService;
import zx.blog.article.utils.ArticleBriefTools;
import zx.blog.cache.container.CacheHolder;
import zx.blog.category.domain.Category;
import zx.blog.common.SystemContext;
import zx.blog.mapper.ArticleMapper;
import zx.blog.mapper.CategoryMapper;
import zx.blog.user.domain.User;
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
	public List<ArticleDto> getArticlesByPage(int pageNum) {
		Map<String, Integer> map = PageUtils.getNextArticlePage(pageNum);
		int start = map.get("start");
		int end = map.get("end");
		List<ArticleDto> articleDtos = articleDao.findOnePageArticle(start, end).stream()
				.map(article->ArticleDto.valueOf(CacheHolder.getUserById(article.getUserId()), CacheHolder.getCagetoryById(article.getCategoryId()), article))
				.collect(Collectors.toList());
		articleDtos.forEach(articleDto->articleDto.setContent(ArticleBriefTools.getBrief(articleDto.getContent(), ARTICLE_BRIEF_LENGTH)));
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
		
		//作者
		User author = CacheHolder.getUserById(article.getUserId());
		//类别
		Category category = CacheHolder.getCagetoryById(article.getCategoryId());
		
		return ArticleDto.valueOf(author, category, article);
	}

	@Override
	public ArticleDto getArticleDtoById(int articleId) {
		Article article= this.articleDao.findById(articleId);
		//作者
		User author = CacheHolder.getUserById(article.getUserId());
		//类别
		Category category = CacheHolder.getCagetoryById(article.getCategoryId());
		return ArticleDto.valueOf(author, category, article);
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
	public List<ArticleDto> getAllArticleDto() {
		List<ArticleDto> articleDtos = this.articleDao.findAll().stream()
				.map(article->ArticleDto.valueOf(CacheHolder.getUserById(article.getUserId()), CacheHolder.getCagetoryById(article.getCategoryId()), article))
				.collect(Collectors.toList());
		articleDtos.forEach(articleDto->articleDto.setContent(ArticleBriefTools.getBrief(articleDto.getContent(), ARTICLE_BRIEF_LENGTH)));
		return articleDtos;
	}

	@Override
	public List<ArticleDto> getArticleByCategory(int categoryId) {
		List<ArticleDto> articleDtos = this.articleDao.findByCategoryId(categoryId).stream()
				.map(article->ArticleDto.valueOf(CacheHolder.getUserById(article.getUserId()), CacheHolder.getCagetoryById(article.getCategoryId()), article))
				.collect(Collectors.toList());
		articleDtos.forEach(articleDto->articleDto.setContent(ArticleBriefTools.getBrief(articleDto.getContent(), ARTICLE_BRIEF_LENGTH)));
		return articleDtos;
	}
	
	@Override
	public void deleteArticle(int articleId){
		Article article = this.articleDao.findById(articleId);
		Category category = CacheHolder.getCagetoryById(article.getCategoryId());
		this.articleDao.deleteArticleByArticleId(articleId);
		category.setTotalArticleNum(category.getTotalArticleNum() - 1);
		this.categoryDao.update(category);
	}
}
