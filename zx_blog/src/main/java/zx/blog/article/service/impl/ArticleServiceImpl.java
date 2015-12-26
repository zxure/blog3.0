package zx.blog.article.service.impl;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import zx.blog.article.domain.Article;
import zx.blog.article.dto.ArticleDto;
import zx.blog.article.service.ArticleService;
import zx.blog.article.utils.ArticleBriefTools;
import zx.blog.cache.core.impl.ArticleCacheManager;
import zx.blog.cache.core.impl.CategoryCacheManager;
import zx.blog.cache.core.impl.UserCacheManager;
import zx.blog.category.domain.Category;
import zx.blog.mapper.ArticleMapper;
import zx.blog.mapper.CategoryMapper;
import zx.blog.system.SystemContext;
import zx.blog.user.domain.User;
import zx.blog.util.PageUtils;

@Component
public class ArticleServiceImpl implements ArticleService{
	@Autowired
	private ArticleCacheManager articleCacheManager;
	@Autowired
	private CategoryCacheManager categoryCacheManager;
	@Autowired
	private UserCacheManager userCacheManager;
	@Autowired
	private ArticleMapper articleDao;
	@Autowired
	private CategoryMapper categoryDao;
	
	private Function<Article, ArticleDto> articleBriefDtoCreatorFunction = null;
	
	@PostConstruct
	public void init(){
		ArticleDtoCreatorFunction articleDtoCreatorFunction = article->{
			User user = userCacheManager.find(User.genKey(article.getUserId()), article.getUserId()).get();
			Category category = categoryCacheManager.find(Category.genKen(article.getCategoryId()), article.getCategoryId()).get();
			return ArticleDto.valueOf(article, user, category);
		};
		
		BiFunction<String, Integer, String> briefContentSupplier = ArticleBriefTools::getBrief;
		
		articleBriefDtoCreatorFunction = (Function<Article, ArticleDto>)(articleDtoCreatorFunction::create);
		
		articleBriefDtoCreatorFunction = articleBriefDtoCreatorFunction.andThen(articleDto->articleDto.setBriefContent(briefContentSupplier));
	}

	@Override
	public boolean addArticle(final Article article) {
		this.articleDao.insert(article);
		SystemContext.articleTotalNum.incrementAndGet();
		int categoryId = article.getCategoryId();
		Category category = categoryCacheManager.find(Category.genKen(categoryId), categoryId).get();
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
		int userId = article.getUserId();
		int categoryId = article.getCategoryId();
		return ArticleDto.valueOf(article, userCacheManager.find(User.genKey(userId), userId).get(), 
				categoryCacheManager.find(Category.genKen(categoryId), categoryId).get());
	}

	@Override
	public ArticleDto getArticleDtoById(int articleId) {
		Article article= this.articleDao.selectById(articleId);
		int userId = article.getUserId();
		int categoryId = article.getCategoryId();
		return ArticleDto.valueOf(article, userCacheManager.find(User.genKey(userId), userId).get(), 
				categoryCacheManager.find(Category.genKen(categoryId), categoryId).get());
	}
	
	@Override
	public Article getArticleById(int articleId){
		return this.articleDao.selectById(articleId);
	}

	@Override
	public void updateArticle(Article article) {
		this.articleDao.update(article);
	}

	@Override
	public List<ArticleDto> getAllArticleDto() {
		List<ArticleDto> articleDtos = articleCacheManager.findAll().stream()
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
		Article article = this.articleDao.selectById(articleId);
		int categoryId = article.getCategoryId();
		Category category = categoryCacheManager.find(Category.genKen(categoryId), categoryId).get();
		this.articleDao.delete(articleId);
		category.setTotalArticleNum(category.getTotalArticleNum() - 1);
		this.categoryDao.update(category);
	}
	
	@FunctionalInterface
	interface ArticleDtoCreatorFunction{
		ArticleDto create(Article article);
	}
}
