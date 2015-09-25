package zx.blog.library.dao;

import zx.blog.library.domain.Library;

public interface LibraryDao {
	
	/**
	 * 插入
	 * @param library
	 */
	public void insert(Library library);
	
	/**
	 * 更新
	 * @param library
	 */
	public void update(Library library);
}
