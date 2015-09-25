package zx.blog.search;

import java.util.List;

import zx.blog.article.domain.Article;

public interface SearchService {
	/**
	 * 全文检索
	 * @param keyWord 检索关键字
	 * @return
	 * @throws Exception 
	 */
	public List<Article> doSearch(String keyWord);

	/**
	 * 添加索引
	 * @param article
	 */
	public void addIndex(Article article);
}
