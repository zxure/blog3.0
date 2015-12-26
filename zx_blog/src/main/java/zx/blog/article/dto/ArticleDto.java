package zx.blog.article.dto;

import java.util.function.BiFunction;

import zx.blog.article.domain.Article;
import zx.blog.category.domain.Category;
import zx.blog.user.domain.User;

/**
 * 文章传值对象
 * @author stonez
 *
 */
public class ArticleDto
{
	/**文章摘要的长度*/
	private static final Integer ARTICLE_BRIEF_LENGTH = Integer.valueOf(300);
	
	//作者名称
	private String authorName;
	//作者头像
	private String authorImgUrl;
	//文章ID
	private int articleId;
	//标题
	private String title;
	//内容
	private String content;
	//类别ID
	private int categoryId;
	//类别名字
	private String categoryName;
	//发布时间
	private String postTimeStr;
	//总浏览次数
	private int totalViewTimes;
	
	/**
	 * 实例化方法
	 * @param article
	 * @return
	 */
	public static ArticleDto valueOf(Article article, User user, Category category){
		ArticleDto articleDto = new ArticleDto();
		articleDto.setAuthorName(user.getUserName());
		articleDto.setAuthorImgUrl(user.getImgUrl());
		articleDto.setArticleId(article.getArticleId());
		articleDto.setTitle(article.getTitle());
		articleDto.setContent(article.getContent());
		articleDto.setCategoryId(category.getCategoryId());
		articleDto.setCategoryName(category.getCategoryName());
		articleDto.setPostTimeStr(article.getPostTime());
		articleDto.setTotalViewTimes(article.getTotalViewTimes());
		return articleDto;
	}
	
	public String getAuthorName()
	{
		return authorName;
	}
	public void setAuthorName(String authorName)
	{
		this.authorName = authorName;
	}
	public String getAuthorImgUrl()
	{
		return authorImgUrl;
	}

	public void setAuthorImgUrl(String authorImgUrl)
	{
		this.authorImgUrl = authorImgUrl;
	}

	public int getArticleId()
	{
		return articleId;
	}
	public void setArticleId(int articleId)
	{
		this.articleId = articleId;
	}
	public String getTitle()
	{
		return title;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}
	public String getContent()
	{
		return content;
	}
	public void setContent(String content)
	{
		this.content = content;
	}
	public int getCategoryId()
	{
		return categoryId;
	}
	public void setCategoryId(int categoryId)
	{
		this.categoryId = categoryId;
	}
	public String getCategoryName()
	{
		return categoryName;
	}
	public void setCategoryName(String categoryName)
	{
		this.categoryName = categoryName;
	}
	public String getPostTimeStr()
	{
		return postTimeStr;
	}
	public void setPostTimeStr(String postTimeStr)
	{
		this.postTimeStr = postTimeStr;
	}
	public int getTotalViewTimes()
	{
		return totalViewTimes;
	}
	public void setTotalViewTimes(int totalViewTimes)
	{
		this.totalViewTimes = totalViewTimes;
	}
	
	public ArticleDto setBriefContent(BiFunction<String, Integer, String> briefContentSupplier)
	{
		this.setContent(briefContentSupplier.apply(this.getContent(), ARTICLE_BRIEF_LENGTH));
		return this;
	}
}
