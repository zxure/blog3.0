package zx.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import zx.blog.article.dto.ArticleDto;
import zx.blog.article.service.ArticleService;
import zx.blog.category.domain.Category;
import zx.blog.category.service.CategoryService;
import zx.blog.util.PageUtils;

@Controller
public class MainControll {
	@Autowired
	private ArticleService articleService;
	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping(value={"/","/index", "index"})
	public ModelAndView index(){
		ModelAndView mdv = new ModelAndView();
		mdv.setViewName("front/index");
		//首页文章信息
		List<ArticleDto> articles = this.articleService.getArticlesByPage(1);
		mdv.addObject("articles", articles);

		//首页类别信息
		List<Category> categoryList = categoryService.findAllCategory();
		mdv.addObject("categories", categoryList);

		//分页信息,当前页3， 12345
		mdv.addObject("pageInfo", PageUtils.getPageInfo(1));
		
		return mdv;
	}
}
