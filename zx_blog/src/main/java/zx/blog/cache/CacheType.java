package zx.blog.cache;

//缓存类型  key 枚举
public enum CacheType {
	/**用户*/
	USER, 
	/**类别*/
	CATEGORY, 
	/**文章*/
	ARTICLE;
	
	public byte[] getKeyBytes(){
		return this.name().getBytes();
	}
}
