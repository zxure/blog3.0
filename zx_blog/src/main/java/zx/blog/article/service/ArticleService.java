package zx.blog.article.service;

import java.util.List;

import zx.blog.article.domain.Article;

public interface ArticleService {
	

	/**
	 * 根据文章ID查找文章 dto
	 * @param articleId
	 * @return
	 */
	public Article getArticleDtoById(int articleId);
	
	/**
	 * 根据文章ID查找文章 
	 * @param articleId
	 * @return
	 */
	public Article getArticleById(int articleId);
	
	/**
	 * 根据类别ID取得对应文章
	 * @param categoryId
	 * @return
	 */
	public List<Article> getArticleByCategory(int categoryId);

	/**
	 * 根据分页拿到文章
	 * @param pageNum
	 * @return
	 */
	public List<Article> getArticlesByPage(int pageNum);
	
	/**
	 * 拿到文章总数
	 * @return
	 */
	public int getArticleTotalNum();
	
	/**
	 * 浏览文章
	 * @param articleId
	 * @return
	 */
	public Article viewArticleById(int articleId);
	
	
	
	/************************* 后台使用 ************************************/
	/**
	 * 写文章
	 * @param article
	 */
	public boolean addArticle(Article article);
	
	/**
	 * 更新一篇文章
	 * @param article
	 */
	public void updateArticle(Article article);
	
	/**
	 * 删除一篇文章
	 */
	public void deleteArticle(int articleId);
	
	/**
	 * 拿到所有文章基本信息
	 * @return
	 */
	public List<Article> getAllSimpleArticleDto();

	/************************* 后台使用 end************************************/

}
