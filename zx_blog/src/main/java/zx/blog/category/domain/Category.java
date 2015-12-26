package zx.blog.category.domain;

import zx.blog.entity.TableRecordVersion;

/**
 * 文章类别
 * @author zxuan
 *
 */
public class Category extends TableRecordVersion{
	private static final long serialVersionUID = -4427126698387274513L;
	private int categoryId;  //类别ID
	private String categoryName;   //类别名称
	private int totalArticleNum; //改类别下的总文章数目
	private String createTime;   //创建时间
	
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public int getTotalArticleNum() {
		return totalArticleNum;
	}
	public void setTotalArticleNum(int totalArticleNum) {
		this.totalArticleNum = totalArticleNum;
	}
	@Override
	public String obtainCacheKey() {
		return Category.class.getSimpleName() + SEPERATOR + this.categoryId;
	}
	
	public static String genKen(int id){
		return Category.class.getSimpleName() + SEPERATOR + id;
	}
}
