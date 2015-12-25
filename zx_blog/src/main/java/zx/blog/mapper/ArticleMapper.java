package zx.blog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import zx.blog.article.domain.Article;

/**
 * aetilce 数据库操作类
 * @author Administrator
 *
 */
public interface ArticleMapper extends BaseMapper<Integer, Article>{
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
	 * 根据类别ID删除文章
	 * @param categoryId
	 */
	public void deleteArticleByCategoryId(Integer categoryId);
	
	/**
	 * 查询一页的文章
	 * @param start  开始
	 * @param end	结束
	 * @return
	 */
	public List<Article> findOnePageArticle(@Param("start")Integer start, @Param("end")Integer end);
	
	/**
	 * 根据类别查询
	 * @param categoryId
	 * @return
	 */
	public List<Article> findByCategoryId(Integer categoryId);
}
