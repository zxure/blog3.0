package zx.blog.article.dto;

public class SimpleArticleDto {
	private int articleId;   //文章ID
	private String title;    //文章标题
	private int authorId;  //作者ID
	private String authorName; //作者名称
	private String authorImgUrl;//作者头像地址
	private int categoryId; //类别ID
	private String categoryName;// 类别名称
	private int postTime;  //发布时间
	private String postTimeStr; //发布时间字符串
	private int totalViewTimes = 0;   //浏览次数
	
	public SimpleArticleDto() {
		super();
	}

	public static SimpleArticleDto valueOf(int articleId, String title, int authorId,
			String authorName, String authorImgUrl, int categoryId,
			String categoryName, int postTime, String postTimeStr, int totalViewTimes) {
		SimpleArticleDto simpleArticleDto = new SimpleArticleDto();
		simpleArticleDto.articleId = articleId;
		simpleArticleDto.title = title;
		simpleArticleDto.authorId = authorId;
		simpleArticleDto.authorName = authorName;
		simpleArticleDto.authorImgUrl = authorImgUrl;
		simpleArticleDto.categoryId = categoryId;
		simpleArticleDto.categoryName = categoryName;
		simpleArticleDto.postTime = postTime;
		simpleArticleDto.postTimeStr = postTimeStr;
		simpleArticleDto.totalViewTimes = totalViewTimes;
		
		return simpleArticleDto;
	}



	public int getArticleId() {
		return articleId;
	}

	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getAuthorId() {
		return authorId;
	}

	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getAuthorImgUrl() {
		return authorImgUrl;
	}

	public void setAuthorImgUrl(String authorImgUrl) {
		this.authorImgUrl = authorImgUrl;
	}

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

	public int getPostTime() {
		return postTime;
	}

	public void setPostTime(int postTime) {
		this.postTime = postTime;
	}

	public int getTotalViewTimes() {
		return totalViewTimes;
	}

	public void setTotalViewTimes(int totalViewTimes) {
		this.totalViewTimes = totalViewTimes;
	}

	public String getPostTimeStr() {
		return postTimeStr;
	}

	public void setPostTimeStr(String postTimeStr) {
		this.postTimeStr = postTimeStr;
	}
}
