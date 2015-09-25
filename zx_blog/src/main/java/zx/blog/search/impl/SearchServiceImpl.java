package zx.blog.search.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import zx.blog.article.domain.Article;
import zx.blog.article.dto.ArticleDto;
import zx.blog.search.SearchService;
import zx.blog.search.lucene.SearchLogic;
import zx.blog.util.ConvertToDto;

@Component
public class SearchServiceImpl implements SearchService{
	@Autowired
	private SearchLogic searchLogic;
	@Override
	public List<ArticleDto> doSearch(String keyWord){
		return this.searchLogic.search(keyWord);
	}
	
	public void addIndex(Article article) {
		this.searchLogic.addIndex(ConvertToDto.convertToArticleDto(article));
	}
}
