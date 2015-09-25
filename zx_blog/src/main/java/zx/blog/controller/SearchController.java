package zx.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import zx.blog.article.dto.ArticleDto;
import zx.blog.search.SearchService;

@Controller
public class SearchController {
	@Autowired
	private SearchService searchService;
	
	@RequestMapping("search")
	public ModelAndView search(String keyWord) throws Exception{
		ModelAndView mdv = new ModelAndView();
		mdv.setViewName("search/result");
		List<ArticleDto> result = this.searchService.doSearch(keyWord);
		System.out.println(result.size());
		mdv.addObject("result", result);
		return mdv;
	}
}
