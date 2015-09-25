package zx.blog.article.domain;


/**
 * 文章
 * @author zxuan
 *
 */
public class Article {
	/**可见状态*/
	public static final int VISIBAL_STATE = 0;
	/**不可见状态*/
	public static final int INVISIBAL_STATE = 1;
	private int articleId;   //文章ID
	private String title;    //文章标题
	private int userId;      //文章作者ID
	private String postTime; //发布日期(时间戳）
	private String lastUpdateTime ; //上次修改时间
	private int categoryId;  //文章类别ID
	private String content;  //文章主体内容
	private int isVisible = Article.VISIBAL_STATE;   //文章是否可见（0：可见     1：不可见）
	private int totalViewTimes = 0;   //总浏览次数
	private int dayViewTimes = 0;   //当日浏览次数
	private int weekViewTimes = 0;   //本周浏览次数
	private int monthViewTimes = 0;   //本月浏览次数
	
	private String briefContent;
	
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
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getPostTime()
	{
		return postTime;
	}
	public void setPostTime(String postTime)
	{
		this.postTime = postTime;
	}
	public String getLastUpdateTime()
	{
		return lastUpdateTime;
	}
	public void setLastUpdateTime(String lastUpdateTime)
	{
		this.lastUpdateTime = lastUpdateTime;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getIsVisible() {
		return isVisible;
	}
	public void setIsVisible(int isVisible) {
		this.isVisible = isVisible;
	}
	public int getTotalViewTimes() {
		return totalViewTimes;
	}
	public void setTotalViewTimes(int totalViewTimes) {
		this.totalViewTimes = totalViewTimes;
	}
	public int getDayViewTimes() {
		return dayViewTimes;
	}
	public void setDayViewTimes(int dayViewTimes) {
		this.dayViewTimes = dayViewTimes;
	}
	public int getWeekViewTimes() {
		return weekViewTimes;
	}
	public void setWeekViewTimes(int weekViewTimes) {
		this.weekViewTimes = weekViewTimes;
	}
	public int getMonthViewTimes() {
		return monthViewTimes;
	}
	public void setMonthViewTimes(int monthViewTimes) {
		this.monthViewTimes = monthViewTimes;
	}
	public String getBriefContent()
	{
		return briefContent;
	}
	public void setBriefContent(String briefContent)
	{
		this.briefContent = briefContent;
	}
}
