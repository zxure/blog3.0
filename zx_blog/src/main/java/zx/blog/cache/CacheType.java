package zx.blog.cache;

import zx.blog.article.domain.Article;
import zx.blog.category.domain.Category;
import zx.blog.entity.TableRecordVersion;
import zx.blog.user.domain.User;

//缓存类型  key 枚举
public enum CacheType {
	/**用户*/
	USER(User.class), 
	/**类别*/
	CATEGORY(Category.class), 
	/**文章*/
	ARTICLE(Article.class);
	
	private Class<? extends TableRecordVersion> clazz;
	
	CacheType(Class<? extends TableRecordVersion> clazz){
		this.clazz = clazz;
	}
	
	public byte[] getKeyBytes(){
		return this.name().getBytes();
	}

	public Class<? extends TableRecordVersion> getClazz() {
		return clazz;
	}
}
