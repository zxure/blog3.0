package zx.blog.category.domain;


/**
 * 文章类别
 * @author zxuan
 *
 */
public class Category {
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
}
