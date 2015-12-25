package zx.blog.library.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import zx.blog.cache.CacheHolder;
import zx.blog.category.domain.Category;
import zx.blog.library.service.LibraryService;

@Controller
public class LibraryController {
	@Autowired
	private LibraryService libraryService;
	
	/**
	 * 实验室首页
	 */
	@RequestMapping({"/library", "/library/"})
	public ModelAndView library(){
		ModelAndView mdv = new ModelAndView();
		//类别信息
		List<Category> categoryList = CacheHolder.getCategoryList();
		mdv.addObject("categories", categoryList);
		mdv.setViewName("front/library/index");
		return mdv;
	}
	
	/**
	 * 访问单个 实验室 项目
	 * @param mappingShortUrl
	 * @return
	 */
	@RequestMapping("/library/view/{mappingShortUrl}")
	public ModelAndView viewLibrary(@PathVariable("mappingShortUrl")String mappingShortUrl){
		ModelAndView mdv = new ModelAndView();
		String viewName = "clock";
		mdv.setViewName("library/clock/" + viewName);
		return mdv;
	}
	
	
	/**
	 * 添加 内容 到 实验室
	 * @return
	 */
	@RequestMapping("/admin/library/add")
	@ResponseBody
	public Map<String, String> addToLibrary(){
		Map<String, String> map = new HashMap<String, String>();
		
		return map;
	}
	
	/**
	 * 更新 实验室中的 一个内容
	 * @return
	 */
	@RequestMapping("/admin/library/update")
	@ResponseBody
	public Map<String, String> updateLibrary(){
		Map<String, String> map = new HashMap<String, String>();
		
		return map;
	}
	
	/**
	 * 删除 实验室的一个内容
	 * @return
	 */
	@RequestMapping("/admin/library/delete")
	@ResponseBody
	public Map<String, String> deleteLibrary(){
		Map<String, String> map = new HashMap<String, String>();
		
		return map;
	}
	
}
