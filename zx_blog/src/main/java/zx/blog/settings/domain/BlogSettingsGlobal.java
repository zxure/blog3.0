package zx.blog.settings.domain;

public class BlogSettingsGlobal {
	
	private int numPrePage; //每页文章数目
	
	private String blogName; //博客名称
	
	private String blogProfile; //博客简介

	public int getNumPrePage() {
		return numPrePage;
	}

	public void setNumPrePage(int numPrePage) {
		this.numPrePage = numPrePage;
	}

	public String getBlogName() {
		return blogName;
	}

	public void setBlogName(String blogName) {
		this.blogName = blogName;
	}

	public String getBlogProfile() {
		return blogProfile;
	}

	public void setBlogProfile(String blogProfile) {
		this.blogProfile = blogProfile;
	}
	
}
