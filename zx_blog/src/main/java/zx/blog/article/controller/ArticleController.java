package zx.blog.article.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import zx.blog.article.domain.Article;
import zx.blog.article.dto.ArticleDto;
import zx.blog.article.service.ArticleService;
import zx.blog.cache.CacheHolder;
import zx.blog.category.domain.Category;
import zx.blog.user.common.UserConstant;
import zx.blog.user.domain.User;
import zx.blog.util.PageUtils;
import zx.blog.util.TimeDateUtil;


@Controller
public class ArticleController {
	
	private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);

	@Autowired
	private ArticleService articleService;
	
	/**
	 * 根据文章ID访问文章
	 * @param articleId
	 * @return
	 */
	@RequestMapping("/article/{articleId}")
	public ModelAndView viewArticleById(@PathVariable("articleId")int articleId){
		ModelAndView mdv = new ModelAndView();
		mdv.setViewName("front/article/detail");
		ArticleDto article = this.articleService.viewArticleById(articleId);
		mdv.addObject("article", article);
		//类别信息
		List<Category> categoryList = CacheHolder.getCategoryList();
		mdv.addObject("categories", categoryList);
		return mdv;
	}
	
	/**
	 * 分页访问，可以返回json
	 * @param pageNum
	 * @return
	 */
	@RequestMapping("/article/page/{pageNum}")
	public ModelAndView viewArticleByPage(@PathVariable("pageNum")int pageNum){
		ModelAndView mdv = new ModelAndView();
		mdv.setViewName("front/index");
		//首页文章信息
		List<ArticleDto> articles = articleService.getArticlesByPage(pageNum);
		mdv.addObject("articles", articles);

		//首页类别信息
		List<Category> categoryList = CacheHolder.getCategoryList();
		mdv.addObject("categories", categoryList);

		//分页信息,当前页3， 12345
		mdv.addObject("pageInfo", PageUtils.getPageInfo(pageNum));
		
		return mdv;
	}
	
	
	/**
	 * 根据类别访问
	 * @param categoryId
	 * @return
	 */
	@RequestMapping("/article/cat/{categoryId}")
	public ModelAndView viewByCategory(@PathVariable("categoryId")int categoryId){
		ModelAndView mdv =new ModelAndView();
		mdv.setViewName("front/index");
		List<ArticleDto> articles = articleService.getArticleByCategory(categoryId);
		
		//首页类别信息
		List<Category> categoryList = CacheHolder.getCategoryList();
		mdv.addObject("categories", categoryList);
		mdv.addObject("articles", articles);
		
		return mdv;
	}
	
	
	
	
	/*******************************后台管理请求******************************************/
	/**
	 * 根据文章ID访问文章， 后端访问一篇文章
	 * @param articleId
	 * @return
	 */
	@RequestMapping("/admin/article/{articleId}")
	public ModelAndView viewArticleByIdForAdmin(@PathVariable("articleId")int articleId){
		ModelAndView mdv =new ModelAndView();
		//设置所有的文章基本信息（名称，作者，时间，类别，标签）
		List<ArticleDto> articles = articleService.getAllArticleDto();

		if(articles != null && articles.size() > 0){
			mdv.addObject("articles", articles);
			//设置最近一篇文章的详细信息
			mdv.addObject("firstArticle", articleService.getArticleDtoById(articleId));
		}
		mdv.setViewName(UserConstant.BACKSTAGE_MAIN_VIEW_NAME);
		return mdv;
	}
	
	/**
	 * 新建博文，跳转到 博文编辑页
	 * @return
	 */
	@RequestMapping("/admin/article/new")
	public ModelAndView writeAticle(){
		ModelAndView mdv =new ModelAndView();
		mdv.addObject("categories", CacheHolder.getSimpleCategoryDtoList());
		mdv.addObject("optType", "postArticle");
		mdv.setViewName("backstage/editor");
		return mdv;
	}
	
	/**
	 * 发布 文章
	 * @param article
	 * @return
	 */
	@RequestMapping("/admin/article/post")
	@ResponseBody
	public Map<String, String> postArticle(int categoryId, String title, String content){
		logger.info("receive title" + title);
		logger.info("receive content" + content);
		Map<String, String> result = new HashMap<String, String>();
		
		int userId = ((User)(this.getRequest().getSession().getAttribute(UserConstant.LOGIN_KEY))).getUserId();
		
		Article article = Article.valueOf(title, content, userId, categoryId);
		articleService.addArticle(article);
		result.put("articleId", article.getArticleId() + "");
		result.put("msgCode", "1");
		result.put("message", "文章发布成功！");
		return result;
	} 
	
	
	/**
	 * 编辑文章
	 * @param articleId
	 * @return
	 */
	@RequestMapping("/admin/editor/{articleId}")
	public ModelAndView editArticle(@PathVariable("articleId")int articleId){
		ModelAndView mdv =new ModelAndView();
		mdv.addObject("categories", CacheHolder.getSimpleCategoryDtoList());
		ArticleDto article = articleService.getArticleDtoById(articleId);
		mdv.addObject("article", article);
		mdv.addObject("optType", "updateArticle");
		mdv.setViewName("backstage/editor");
		return mdv;
	}
	
	/**
	 * 更新博文
	 * @param article
	 * @return
	 */
	@RequestMapping("/admin/article/update")
	@ResponseBody
	public Map<String, String> updateArticle(int categoryId, String title, int articleId, String content ){
		logger.info("更新文章，文章ID为：" + articleId);
		Map<String, String> map = new HashMap<String, String>();
		Article article = articleService.getArticleById(articleId);
		article.setCategoryId(categoryId);
		article.setTitle(title);
		article.setContent(content);
		article.setLastUpdateTime(TimeDateUtil.getCurrentTimestr());
		articleService.updateArticle(article);
		map.put("msgCode", "1");
		map.put("message", "文章更新成功！");
		return map;
	}
	
	/**
	 * 删除文章
	 * @return
	 */
	@RequestMapping("/admin/article/delete")
	@ResponseBody
	public Map<String, String> deleteArticle(int articleId){
		logger.info("删除文章， 文章ID为：" + articleId);
		Map<String, String> map = new HashMap<String, String>();
		this.articleService.deleteArticle(articleId);
		map.put("msgCode", "1");
		map.put("message", "文章删除成功！");
		return map;
	}
	
	//获取request
	public HttpServletRequest getRequest(){
		return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
	}

}
