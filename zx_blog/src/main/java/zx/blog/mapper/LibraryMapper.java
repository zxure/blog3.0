package zx.blog.mapper;

import zx.blog.library.domain.Library;

public interface LibraryMapper {
	
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
