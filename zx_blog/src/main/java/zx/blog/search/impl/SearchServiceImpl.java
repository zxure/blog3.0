package zx.blog.search.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import zx.blog.article.domain.Article;
import zx.blog.search.SearchService;
import zx.blog.search.lucene.SearchLogic;

@Component
public class SearchServiceImpl implements SearchService{
	@Autowired
	private SearchLogic searchLogic;
	@Override
	public List<Article> doSearch(String keyWord){
		return this.searchLogic.search(keyWord);
	}
	
	public void addIndex(Article article) {
		this.searchLogic.addIndex(article);
	}
}
