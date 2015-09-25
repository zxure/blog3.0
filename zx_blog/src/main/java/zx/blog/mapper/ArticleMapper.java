package zx.blog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import zx.blog.article.domain.Article;

public interface ArticleMapper {
	/**
	 * 新增文章
	 * @param article
	 */
	public void insert(Article article);
	/**
	 * 更新文章
	 * @param article
	 */
	public void update(Article article);
	
	/**
	 * 查找全部文章
	 * @return
	 */
	public List<Article> findAll();
	
	/**
	 * 查询可见文章总数目
	 * @return
	 */
	public int getArticleTotalNum();
	
	/**
	 * 根据ID查找文章
	 * @param i
	 * @return
	 */
	public Article findById(@Param("articleId")int articleId);
	
	/**
	 * 根据类别ID删除文章
	 * @param categoryId
	 */
	public void deleteArticleByCategoryId(int categoryId);
	
	/**
	 * 查询一页的文章
	 * @param start  开始
	 * @param end	结束
	 * @return
	 */
	public List<Article> findOnePageArticle(@Param("start")int start, @Param("end")int end);
	
	/**
	 * 根据类别查询
	 * @param categoryId
	 * @return
	 */
	public List<Article> findByCategoryId(int categoryId);
	
	/**
	 * 根据文章ID删除文章
	 * @param articleId
	 */
	public void deleteArticleByArticleId(int articleId);

}
